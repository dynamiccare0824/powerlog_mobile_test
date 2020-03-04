package test.powerlog.mobile.springboot.service.mobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.powerlog.mobile.springboot.domain.view.UserAccountVw;
import test.powerlog.mobile.springboot.domain.view.UserAccountVwRepository;
import test.powerlog.mobile.springboot.service.mobile.impl.SendEmailServiceImpl;
import test.powerlog.mobile.springboot.service.mobile.old.ResetPasswordService;
import test.powerlog.mobile.springboot.service.mobile.old.ResetUidService;
import test.powerlog.mobile.springboot.web.dto.mobile.request.EmailFormDto;

import java.util.HashMap;
import java.util.Optional;

@Service
public class CheckService {

    @Autowired
    private UserAccountVwRepository userAccountVwRepository;
    @Autowired
    private SendEmailServiceImpl sendEmailService;
    @Autowired
    private ResetUidService resetUidService;
    @Autowired
    private ResetPasswordService resetPasswordService;

    /*로그인 요청 처리*/
    public HashMap<String, Object> EmailPasswordCheck(String email, String password) {

        HashMap<String, Object> tmpMap = new HashMap();
        Optional<UserAccountVw> record = userAccountVwRepository.findById(email);

        if(record.isPresent()){
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
        else{
            tmpMap.put("isMatch", false);
            tmpMap.put("error", "record.isPresent(): false");
            tmpMap.put("name", null);
        }
        return tmpMap;
    }

    public HashMap<String, Object> DupCheckEmail(String email) {
        HashMap<String, Object> tmpMap = new HashMap<>();
        if (userAccountVwRepository.findById(email).isPresent()) {
            tmpMap.put("emailPresent", true);
            tmpMap.put("error", null);
        } else {
            tmpMap.put("emailPresent", false);
            tmpMap.put("error", null);
        }
        return tmpMap;
    }

    public HashMap<String, Object> DupCheckPhone(String phone){
        HashMap<String, Object> tmpMap = new HashMap<>();

        Optional<UserAccountVw> record = Optional.ofNullable(userAccountVwRepository.findByLoginVwPhone(phone));
        if (record.isPresent()) {
            tmpMap.put("phonePresent", true);
            tmpMap.put("error", null);
        } else {
            tmpMap.put("phonePresent", false);
            tmpMap.put("error", null);
        }
        return tmpMap;
    }

    public boolean emailPhoneCheck(String email, String phone) {
        boolean result = false;
        System.out.println(email + phone);
//        System.out.println(userAccountVWRepository.findById(email).get().getLoginVwEmail());
        try {
            System.out.println(userAccountVwRepository.findById(email));
            Optional<UserAccountVw> record = userAccountVwRepository.findById(email);

            if (record.get().getLoginVwEmail().equals(email) && record.get().getLoginVwPhone().equals(phone)) {
                System.out.println(record.get().getLoginVwEmail());
                System.out.println("Correct");
                result = true;
            } else {
                System.out.println(record.get().getLoginVwEmail());
                System.out.println("Error");
                result = false;
            }//            return (record.get().getId() == testProductDto.getId() && record.get().getPassword() == testProductDto.getPassword()) ? true: false
        } catch (Exception ex) {
            System.out.println(ex);
        }
        System.out.println("EmailPhoneCheckDone");
        return result;
    }

    /*로그인 요청 처리*/
    public boolean emailQuestionCheck(String email, String questionCode, String questionAnswer, String number) {
        boolean result = false;
        try {
            System.out.println(userAccountVwRepository.findById(email));
            Optional<UserAccountVw> record = userAccountVwRepository.findById(email);
            String name = record.get().getLoginVwName();

            if (record.get().getLoginVwEmail().equals(email) && record.get().getLoginVwQCode().equals(questionCode)
                    && record.get().getLoginVwQAnswer().equals(questionAnswer)) {
                System.out.println(record.get().getLoginVwEmail());
                System.out.println("Correct");

                EmailFormDto emailFormDto = new EmailFormDto();
                emailFormDto.setSender("noreply.dynamiccare@gmail.com");
                emailFormDto.setContent("파워로그 임시 비밀번호 " + number + " 입니다. \n로그인 후 비밀번호 재설정을 통해 비밀번호를 변경할 수 있습니다." +
                        "\n이 이메일 주소는 발신 전용 주소입니다. 회신이 불가능합니다." );
                emailFormDto.setRecipient(email);
                emailFormDto.setSubject("[파워로그] " + name + " 고객님, 안녕하세요! 발급된 임시 비밀번호를 확인하세요");
                sendEmailService.sendMail(emailFormDto);

                resetPasswordService.ResetPassword(email, number);
                result = true;
            } else {
                System.out.println(record.get().getLoginVwEmail());
                System.out.println("Error");
                result = false;
            }//            return (record.get().getId() == testProductDto.getId() && record.get().getPassword() == testProductDto.getPassword()) ? true: false
        } catch (Exception ex) {
            System.out.println(ex);
        }
        System.out.println("EmailQuestionCheckDone");
        return result;
    }
}
