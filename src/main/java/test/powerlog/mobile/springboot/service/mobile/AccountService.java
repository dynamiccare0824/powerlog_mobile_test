package test.powerlog.mobile.springboot.service.mobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.powerlog.mobile.springboot.domain.table.UserTb;
import test.powerlog.mobile.springboot.domain.table.UserTbRepository;
import test.powerlog.mobile.springboot.domain.view.LogLateMsrVw;
import test.powerlog.mobile.springboot.domain.view.LogLateMsrVwRepository;
import test.powerlog.mobile.springboot.domain.view.UserAccountVw;
import test.powerlog.mobile.springboot.domain.view.UserAccountVwRepository;
import test.powerlog.mobile.springboot.service.mobile.old.SignUpService;
import test.powerlog.mobile.springboot.web.dto.mobile.request.SignUpDto;

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
    private SignUpService signUpService;
    @Autowired
    private LogLateMsrVwRepository logLateMsrVwRepository;


    /*로그인 요청 처리*/
    public HashMap<String, Object> EmailPasswordCheck(String email, String password) {

        HashMap<String, Object> tmpMap = new HashMap();
        Optional<UserAccountVw> record = userAccountVwRepository.findById(email);

        if (record.isPresent()) {
            // 레코드가 존재하는데 조건에 맞다면 제대로 입력한 것
            if (record.get().getLoginVwEmail().equals(email) && record.get().getLoginVwPassword().equals(password)) {
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

                if(commonCode.contains("01")){
                    isWorkoutCode.add(logList.get().get(i).getLgLateMsrVwCommonCode());
                    HashMap<String, Object> codeDetailMap = (HashMap<String, Object>) workoutCodeMap.get((String) commonCode);
                    String workoutMax = logList.get().get(i).getLgLateMsrVwMax();
                    codeDetailMap.replace("WorkoutMax", logList.get().get(i).getLgLateMsrVwMax());
                    codeDetailMap.replace("WorkoutRank", String.format("%,.1f", random.nextFloat()*100));
                    workoutCodeMap.replace("commonCode", codeDetailMap);
                }
            }
            workoutCodeMap.put("presentWorkoutCode", isWorkoutCode);
        }
        else{
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


    /*로그인 요청 처리*/
    public boolean ResetPassword(String email, String password) {
        boolean result = false;
        try {
            LocalDateTime localDateTime = LocalDateTime.now();
            System.out.println(password + "1");
            System.out.println(userTbRepository.findById(email));
            Optional<UserTb> record = userTbRepository.findById(email);

            SignUpDto signUpDto = SignUpDto.builder().email(record.get().getUEmail()).password(password).uid(record.get().getUUid()).name(record.get().getUName())
                    .gender(record.get().getUGender()).birth(record.get().getUBirth()).height(record.get().getUHeight()).weight(record.get().getUWeight())
                    .agreeFlag(record.get().getUAgreeFlag()).personalFlag(record.get().getUAgreeFlag()).shapeCode(record.get().getUShapeCode()).qAnswer(record.get().getUQAnswer()).qCode(record.get().getUQCode())
                    .verification(record.get().getUVerification()).phone(record.get().getUPhone())
                    .createdTime(record.get().getUCreatedTime()).updatedTime(localDateTime).career(record.get().getUCareer()).build();
            System.out.println(signUpDto.getPassword() + "2");
            signUpService.Signup(signUpDto); // save 실행
            System.out.println(record.get().getUEmail());
            signUpService.Signup(signUpDto); // save 실행
            System.out.println("Correct");
            result = true;
//                return (record.get().getId() == testProductDto.getId() && record.get().getPassword() == testProductDto.getPassword()) ? true: false
        } catch (Exception ex) {
            System.out.println(ex);
        }
        System.out.println("EmailQuestionCheckDone");
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
