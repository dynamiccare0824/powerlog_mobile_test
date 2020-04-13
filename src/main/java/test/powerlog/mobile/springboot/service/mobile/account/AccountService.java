package test.powerlog.mobile.springboot.service.mobile.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.powerlog.mobile.springboot.domain.table.PlannerByProgramTb;
import test.powerlog.mobile.springboot.domain.table.PlannerByProgramTbRepository;
import test.powerlog.mobile.springboot.domain.table.UserTb;
import test.powerlog.mobile.springboot.domain.table.UserTbRepository;
import test.powerlog.mobile.springboot.domain.view.LogLateMsrVw;
import test.powerlog.mobile.springboot.domain.view.LogLateMsrVwRepository;
import test.powerlog.mobile.springboot.domain.view.UserAccountVw;
import test.powerlog.mobile.springboot.domain.view.UserAccountVwRepository;
import test.powerlog.mobile.springboot.service.mobile.account.SignUpService;
import test.powerlog.mobile.springboot.web.dto.mobile.request.account.ReqUpdateBodyDetailDto;
import test.powerlog.mobile.springboot.web.dto.mobile.request.account.ReqRegisterDto;
import test.powerlog.mobile.springboot.web.dto.mobile.request.account.SignUpDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class AccountService {

    @Autowired
    private UserTbRepository userTbRepository;
    @Autowired
    private UserAccountVwRepository userAccountVwRepository;
    @Autowired
    private SendEmailService sendEmailService;
    @Autowired
    private NumberGenService numberGenService;
    @Autowired
    private SignUpService signUpService;
    @Autowired
    private LogLateMsrVwRepository logLateMsrVwRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private PlannerByProgramTbRepository plannerByProgramTbRepository;


    /*로그인 요청 처리*/
    public HashMap<String, Object> EmailPasswordCheck(String email, String password) {

        HashMap<String, Object> tmpMap = new HashMap();
        Optional<UserAccountVw> record = userAccountVwRepository.findById(email);

        if (record.isPresent()) {
            // 레코드가 존재하는데 조건에 맞다면 제대로 입력한 것
            if (record.get().getLoginVwEmail().equals(email) && passwordEncoder.matches(password, record.get().getLoginVwPassword())) {
                String name = record.get().getLoginVwName();
                tmpMap.put("isMatch", true);
                tmpMap.put("error", null);
                tmpMap.put("name", name);
            }
            // 레코드가 존재하는데 조건에 맞지 않는다면 잘못된 등록 정보라는 것
            else {
                tmpMap.put("isMatch", false);
                tmpMap.put("error", null);
                tmpMap.put("name", null);
            }
        }
        //레코드가 존재하지 않는다면 잘못된 등록 정보라는 것
        else {
            tmpMap.put("isMatch", false);
            tmpMap.put("error", "record.isPresent(): false");
            tmpMap.put("name", null);
        }
        return tmpMap;
    }

    public Boolean ifExpired(String email){
        Boolean result = false;
        Optional<List<PlannerByProgramTb>> logList = Optional.ofNullable(plannerByProgramTbRepository.findAllByPlnEmailOrderByPlnOnDateDesc(email));
        if(logList.get().size() > 0){
            LocalDate logDate = logList.get().get(0).getPlnOnDate();
            LocalDate curDate = LocalDate.now();
            if(logDate.isBefore(curDate)){
                result = true;
            }
        }
        return result;
    }


    public HashMap<String, Object> LgLateMsrVwEmailMap(String email, HashMap<String, Object> workoutCodeMap) {
        Random random = new Random();


        HashMap<String, Object> resultMap = new HashMap<>();
        Optional<List<LogLateMsrVw>> logList = Optional.ofNullable(logLateMsrVwRepository.findAllByLgLateMsrVwEmail(email));
        ArrayList<String> isWorkoutCode = new ArrayList<>();
        if (logList.isPresent()) {
            for (int i = 0; i < logList.get().size(); i++) {
                HashMap<String, Object> tmpMap = new HashMap<>();
                HashMap<String, Object> tmpMap2 = new HashMap<>();
                String commonCode = logList.get().get(i).getLgLateMsrVwCommonCode();

                if (commonCode.contains("01")) {
                    int rank = 0;
                    Optional<List<LogLateMsrVw>> codeList = Optional.ofNullable(logLateMsrVwRepository.findAllByLgLateMsrVwCommonCodeOrderByLgLateMsrVwMaxDesc(commonCode));
                    for (int j = 0; j < codeList.get().size(); j++) {
                        if (codeList.get().get(j).getLgLateMsrVwEmail().equals(email)) {
                            rank = j;
                            break;
                        }
                    }

                    double workoutRank = (double) (rank + 1) / codeList.get().size() * 100;
                    if ((rank + 1) == codeList.get().size()) {
                        workoutRank = 99.9;
                    }

                    isWorkoutCode.add(commonCode);
                    HashMap<String, Object> codeDetailMap = (HashMap<String, Object>) workoutCodeMap.get((String) commonCode);
                    String workoutMax = Integer.toString(logList.get().get(i).getLgLateMsrVwMax());
                    codeDetailMap.replace("WorkoutMax", workoutMax);
//                    codeDetailMap.replace("WorkoutRank", String.format("%,.1f", random.nextFloat() * 100));
                    codeDetailMap.replace("WorkoutRank", String.format("%,.1f", workoutRank));
                    workoutCodeMap.replace("commonCode", codeDetailMap);
                }
            }
            workoutCodeMap.put("presentWorkoutCode", isWorkoutCode);
        } else {
            workoutCodeMap.put("presentWorkoutCode", null);
        }
        return workoutCodeMap;
    }

    public static HashMap<String, ArrayList<String>> joinDic(String[] arr) {
        // ①
        HashMap<String, ArrayList<String>> dic = new HashMap<String, ArrayList<String>>();
        for (int i = 0; i < arr.length; i++) {
            String[] line = arr[i].split(",");  // ②
            String key = line[5]; //③

            ArrayList<String> list = new ArrayList<String>();
            if (dic.containsKey(key)) { //⑤
                list = dic.get(key);  //⑤-1
                list.add(line[13]);
            } else {
                list.add(line[13]); //⑤-2
            }
            dic.put(key, list); //⑥
        }
        for (String key : dic.keySet()) {  //⑦
            System.out.println(key);
        }
        return dic;
    }


    /*회원가입 요청 처리*/
    @Transactional
    public String Signup(SignUpDto signUpDto) {
        return userTbRepository.save(signUpDto.toEntity()).getUEmail();
    }

    //
    public SignUpDto ToRegisterForm(ReqRegisterDto reqRegisterDto) {

        LocalDateTime localDateTime = LocalDateTime.now();
        String tmpUid = numberGenService.ComplicatedDigits(12, 1);
        int careerY = Integer.parseInt(reqRegisterDto.getCareerYear());
        int careerM = Integer.parseInt(reqRegisterDto.getCareerMonth());

        SignUpDto signUpDto = SignUpDto.builder().email(reqRegisterDto.getEmail()).password(reqRegisterDto.getPassword()).uid(tmpUid).name(reqRegisterDto.getName())
                .gender(reqRegisterDto.getGender()).birth(reqRegisterDto.getBirth()).height(Integer.parseInt(reqRegisterDto.getHeight())).weight(Integer.parseInt(reqRegisterDto.getWeight()))
                .agreeFlag("true").personalFlag("true").shapeCode(reqRegisterDto.getShapeCode()).qAnswer(reqRegisterDto.getQuestionAnswer()).qCode(reqRegisterDto.getQuestionCode())
                .verification("true").phone(reqRegisterDto.getPhone()).createdTime(localDateTime).updatedTime(localDateTime).career(careerM + careerY * 12).build();
        return signUpDto;
    }

    public HashMap<String, Object> DeleteAccount(HashMap<String, Object> map, String email) {
        if ((Boolean) map.get("isMatch")) {
            try {
                userTbRepository.deleteById(email);
            } catch (Exception ex) {
                map.replace("error", ex.toString());
            }
        }
        return map;
    }

    public boolean UpdateBodyDetail(ReqUpdateBodyDetailDto updateBodyDetailDto) {
        boolean result;
        LocalDateTime localDateTime = LocalDateTime.now();

        Optional<UserTb> record = userTbRepository.findById(updateBodyDetailDto.getEmail());
        if (record.isPresent()) {
            SignUpDto signUpDto = SignUpDto.builder()
                    .email(record.get().getUEmail())
                    .password(record.get().getUPassword())
                    .uid(record.get().getUUid())
                    .name(record.get().getUName())
                    //여기서부터 바꾼다
                    .gender(updateBodyDetailDto.getGender())
                    .birth(updateBodyDetailDto.getBirth())

                    .height(Integer.parseInt(updateBodyDetailDto.getHeight()))
                    .weight(Integer.parseInt(updateBodyDetailDto.getWeight()))
                    //바꾼다 끝
                    .agreeFlag(record.get().getUAgreeFlag())
                    .personalFlag(record.get().getUAgreeFlag())
                    .shapeCode(record.get().getUShapeCode())
                    .qAnswer(record.get().getUQAnswer())
                    .qCode(record.get().getUQCode())
                    .verification(record.get().getUVerification())
                    .phone(record.get().getUPhone())
                    .createdTime(record.get().getUCreatedTime())
                    .updatedTime(localDateTime)
                    .career(record.get().getUCareer()).build();
            signUpService.Signup(signUpDto); // save 실행
            System.out.println("Correct");
            result = true;
        } else {
            result = false;
        }
        return result;
    }


    /*로그인 요청 처리*/
    public boolean ResetPassword(String email, String password) {
        boolean result = false;
        try {
            LocalDateTime localDateTime = LocalDateTime.now();
            Optional<UserTb> record = userTbRepository.findById(email);

            SignUpDto signUpDto = SignUpDto.builder().email(record.get().getUEmail()).password(password).uid(record.get().getUUid()).name(record.get().getUName())
                    .gender(record.get().getUGender()).birth(record.get().getUBirth()).height(record.get().getUHeight()).weight(record.get().getUWeight())
                    .agreeFlag(record.get().getUAgreeFlag()).personalFlag(record.get().getUAgreeFlag()).shapeCode(record.get().getUShapeCode()).qAnswer(record.get().getUQAnswer()).qCode(record.get().getUQCode())
                    .verification(record.get().getUVerification()).phone(record.get().getUPhone())
                    .createdTime(record.get().getUCreatedTime()).updatedTime(localDateTime).career(record.get().getUCareer()).build();
            signUpService.Signup(signUpDto); // save 실행
            signUpService.Signup(signUpDto); // save 실행
            result = true;
//                return (record.get().getId() == testProductDto.getId() && record.get().getPassword() == testProductDto.getPassword()) ? true: false
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return result;
    }

    /*로그인 요청 처리*/
    public boolean ResetShapeCode(String email, String shapeCode) {
        boolean result = false;
        try {
            LocalDateTime localDateTime = LocalDateTime.now();
            Optional<UserTb> record = userTbRepository.findById(email);

            SignUpDto signUpDto = SignUpDto.builder().email(record.get().getUEmail()).password(record.get().getUPassword()).uid(record.get().getUUid()).name(record.get().getUName())
                    .gender(record.get().getUGender()).birth(record.get().getUBirth()).height(record.get().getUHeight()).weight(record.get().getUWeight())
                    .agreeFlag(record.get().getUAgreeFlag()).personalFlag(record.get().getUAgreeFlag()).shapeCode(shapeCode).qAnswer(record.get().getUQAnswer()).qCode(record.get().getUQCode())
                    .verification(record.get().getUVerification()).phone(record.get().getUPhone())
                    .createdTime(record.get().getUCreatedTime()).updatedTime(localDateTime).career(record.get().getUCareer()).build();
            signUpService.Signup(signUpDto); // save 실행
            System.out.println(record.get().getUEmail());
            signUpService.Signup(signUpDto); // save 실행
            System.out.println("Correct");
            result = true;
        } catch (Exception ex) {
            System.out.println(ex);
        }
        System.out.println("EmailQuestionCheckDone");
        return result;
    }

    public boolean ResetUid(String email, String randNum) {
        boolean result = false;
        try {
            LocalDateTime localDateTime = LocalDateTime.now();

            Optional<UserTb> record = userTbRepository.findById(email);

            SignUpDto signUpDto = SignUpDto.builder().email(record.get().getUEmail()).password(record.get().getUPassword()).uid(randNum).name(record.get().getUName())
                    .gender(record.get().getUGender()).birth(record.get().getUBirth()).height(record.get().getUHeight()).weight(record.get().getUWeight())
                    .agreeFlag(record.get().getUAgreeFlag()).personalFlag(record.get().getUAgreeFlag()).shapeCode(record.get().getUShapeCode()).qAnswer(record.get().getUQAnswer()).qCode(record.get().getUQCode())
                    .verification(record.get().getUVerification()).phone(record.get().getUPhone())
                    .createdTime(record.get().getUCreatedTime()).updatedTime(localDateTime).career(record.get().getUCareer()).build();
            signUpService.Signup(signUpDto); // save 실행
            System.out.println(record.get().getUEmail());
            signUpService.Signup(signUpDto); // save 실행
            System.out.println("Correct");
            result = true;
        } catch (Exception ex) {
            System.out.println(ex);
        }
        System.out.println("EmailQuestionCheckDone");
        return result;
    }


    /*로그인 요청 처리*/
    public boolean UpdatePhone(String email, String phone) {
        boolean result = false;
        try {
            LocalDateTime localDateTime = LocalDateTime.now();

            Optional<UserTb> record = userTbRepository.findById(email);

            SignUpDto signUpDto = SignUpDto.builder().email(record.get().getUEmail()).password(record.get().getUPassword()).uid(record.get().getUUid()).name(record.get().getUName())
                    .gender(record.get().getUGender()).birth(record.get().getUBirth()).height(record.get().getUHeight()).weight(record.get().getUWeight())
                    .agreeFlag(record.get().getUAgreeFlag()).personalFlag(record.get().getUAgreeFlag()).shapeCode(record.get().getUShapeCode()).qAnswer(record.get().getUQAnswer()).qCode(record.get().getUQCode())
                    .verification(record.get().getUVerification()).phone(phone)
                    .createdTime(record.get().getUCreatedTime()).updatedTime(localDateTime).career(record.get().getUCareer()).build();
            signUpService.Signup(signUpDto); // save 실행
            System.out.println("Correct");
            result = true;
        } catch (Exception ex) {
            System.out.println(ex);
        }
        System.out.println("ResetPhone");
        return result;
    }
}
