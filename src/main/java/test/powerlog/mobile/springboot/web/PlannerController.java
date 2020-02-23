package test.powerlog.mobile.springboot.web;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import test.powerlog.mobile.springboot.domain.products.*;
import test.powerlog.mobile.springboot.service.*;
import test.powerlog.mobile.springboot.web.dto.LogLateMsrDto;
import test.powerlog.mobile.springboot.web.dto.SignUpDto;
import test.powerlog.mobile.springboot.web.dto.UserAccountDto;

import java.util.HashMap;

@RestController
public class PlannerController {

    @Autowired
    private UserAccountVwRepository userAccountVWRepository;

    @Autowired
    private LogLateMsrVwRepository logLateMsrVwRepository;

    @Autowired
    private LogTotalWrkotVwRepository logTotalWrkotVwRepository;

    @Autowired
    LoginService loginService;

    @Autowired
    EmailPhoneCheckService emailPhoneCheckService;

    @Autowired
    EmailQuestionCheckService emailQuestionCheckService;

    @Autowired
    SignUpService signUpService;

    @Autowired
    ResetPasswordService resetPasswordService;

    @Autowired
    ResetUidService resetUidService;

    @Autowired
    ResetShapeCodeService resetShapeCodeService;

    @Autowired
    ResetPhoneService resetPhoneService;

    @Autowired
    DeleteAccountService deleteAccountService;

    @PostMapping(value = "/plannerByMonth")
    public HashMap<String, Object> PlannerByMonth(@RequestBody UserAccountDto userAccountDto) throws JsonProcessingException {
        HashMap<String, Object> resultMap = new HashMap();
        String email = userAccountDto.getEmail();

        try{
            resultMap.put("result", logTotalWrkotVwRepository.findAllByLgTotalWrkotVwEmail(email));
            resultMap.put("error", null);
        }
        // 아이디가 아예 존재하지 않는 경우
        catch(Exception ex){
            resultMap.put("result", null);
            resultMap.put("error", ex.toString());
        }
        return resultMap;
    }

//    @PostMapping(value = "/signUpCheckEmail")
//    public HashMap<String, Object> SignUpCheckEmail(@RequestBody UserAccountDto userAccountDto) throws JsonProcessingException {
//        HashMap<String, Object> resultMap = new HashMap();
//        String id = userAccountDto.getEmail();
//
//        //중복 아이디가 있는 경우
//        try{
//            System.out.println(userAccountVWRepository.findById(id).get().getLoginVwEmail());
//            //같은 아이디를 찾을 수 없다면 여기서 catch로 넘어가게 될 것임
//            resultMap.put("emailPresent", true);
//            resultMap.put("error", null);
//        }
//        // 중복 아이디가 없는 경우
//        catch(Exception ex){
//            resultMap.put("emailPresent", false);
//            resultMap.put("error", ex.toString());
//        }
//        return resultMap;
//    }
//
//    @PostMapping(value = "/signUpSendMsg")
//    public HashMap<String, Object> SignUpSendMsg(@RequestBody UserAccountDto userAccountDto) throws JsonProcessingException {
//        HashMap<String, Object> tmpMap = new HashMap();
//        HashMap<String, Object> resultMap = new HashMap();
//        SendMsgService_New sendMsgService_new = new SendMsgService_New();
//        String phone = userAccountDto.getPhone();
//        ObjectMapper mapper = new ObjectMapper();
//        NumberGen numberGen = new NumberGen();
//        String randNum = numberGen.four_digits(4, 1);
//        String[] numbers = {"99999999999"};
//        try{
//            userAccountVWRepository.findByLoginVwPhone(phone).getLoginVwPhone();
//            resultMap.put("phonePresent", true);
//            resultMap.put("verificationNum", randNum);
//            resultMap.put("error", "isPresentError");
//        }
//        catch(Exception ex){
//            System.out.println(ex);
////             전화번호가 존재하지 않은 경우에만 메시지를 보낸다
//
//            numbers[0] = phone;
//            tmpMap.put("type", "SMS");
//            tmpMap.put("from", "01050055438");
//            tmpMap.put("to", numbers);
//            tmpMap.put("content", "인증번호 [" + randNum + "] 숫자 4자리를 입력해주세요 - 파워로그 모바일");
//            String json = mapper.writeValueAsString(tmpMap);
//
//            sendMsgService_new.NewSend("https://api-sens.ncloud.com/v1/sms/services/ncp:sms:kr:258080742855:testpowerlog/messages", json);
//
//            resultMap.put("phonePresent", false);
//            resultMap.put("verificationNum", randNum);
//            resultMap.put("error", null);
//        }
//        return resultMap;
//    }
//
//    @PostMapping(value = "/signUp")
//    public HashMap<String, Object> SignUp(@RequestBody UserAccountDto userAccountDto) throws JsonProcessingException {
//
//        HashMap<String, Object> resultMap = new HashMap();
//        NumberGen numberGen = new NumberGen();
//
//        java.util.Date utilDate = new java.util.Date();
//        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
//        String tmpUid = numberGen.four_digits(8, 1);
//        int careerY = userAccountDto.getCareer_year();
//        int careerM = userAccountDto.getCareer_month();
//
//        System.out.println(userAccountDto.getQuestionCode());
//        System.out.println(userAccountDto.getQuestionAnswer());
//
//
//
//        SignUpDto signUpDto  = SignUpDto.builder().email(userAccountDto.getEmail()).password(userAccountDto.getPassword()).uid(tmpUid).name(userAccountDto.getName())
//                .gender(userAccountDto.getGender()).birth(userAccountDto.getBirth()).height(userAccountDto.getHeight()).weight(userAccountDto.getWeight())
//                .agreeFlag(userAccountDto.getAgreeFlag()).personalFlag(userAccountDto.getAgreeFlag()).shapeCode(userAccountDto.getShapeCode()).qAnswer(userAccountDto.getQuestionAnswer()).qCode(userAccountDto.getQuestionCode())
//                .verification(userAccountDto.getVerification()).phone(userAccountDto.getPhone()).createdTime(sqlDate).updatedTime(sqlDate).career(careerM + careerY * 12).build();
//        signUpService.Signup(signUpDto); // save 실행
//
//        try{
//            String findById = userAccountVWRepository.findById(userAccountDto.getEmail()).get().getLoginVwEmail();
//            resultMap.put("findById", findById);
//            resultMap.put("result", true);
//            resultMap.put("error", null);
//        }
//        catch(Exception ex){
//            resultMap.put("findById", ex);
//            resultMap.put("result", false);
//            resultMap.put("error", ex.toString());
//        }
//
//        return resultMap;
//    }
//
//    @PostMapping(value = "/resetCheckEmailPhone")
//    public HashMap<String, Object> ResetCheckEmailPhone(@RequestBody UserAccountDto userAccountDto) throws JsonProcessingException {
//
//        HashMap<String, Object> resultMap = new HashMap();
//        HashMap<String, Object> tmpMap = new HashMap();
//        NumberGen numberGen = new NumberGen();
//        ObjectMapper mapper = new ObjectMapper();
//        SendMsgService_New sendMsgService_new = new SendMsgService_New();
//
//        String phone = userAccountDto.getPhone();
//        String email = userAccountDto.getEmail();
//        String[] numbers = {"99999999999"};
//        String randNum =  numberGen.four_digits(4, 1);
//
//        try{
//            Boolean result = emailPhoneCheckService.emailPhoneCheck(email, phone);
//            if(result){
//                numbers[0] = phone;
//                tmpMap.put("type", "SMS");
//                tmpMap.put("from", "01050055438");
//                tmpMap.put("to", numbers);
//                tmpMap.put("content", "인증번호 [" + randNum + "] 숫자 4자리를 입력해주세요 - 파워로그 모바일");
//                String json = mapper.writeValueAsString(tmpMap);
//
//                sendMsgService_new.NewSend("https://api-sens.ncloud.com/v1/sms/services/ncp:sms:kr:258080742855:testpowerlog/messages", json);
//            }
//            resultMap.put("verificationNum", randNum);
//            resultMap.put("match", result);
//            resultMap.put("error", null);
//        }
//        catch(Exception ex){
//            resultMap.put("verificationNum", randNum);
//            resultMap.put("match", false);
//            resultMap.put("error", ex.toString());
//            System.out.println(ex);
//        }
//        return resultMap;
//    }
//
//    @PostMapping(value = "/resetCheckEmailQuestion")
//    public HashMap<String, Object> ResetCheckEmailQuestion(@RequestBody UserAccountDto userAccountDto) throws JsonProcessingException {
//
//        HashMap<String, Object> resultMap = new HashMap();
//        NumberGen numberGen = new NumberGen();
//
//        String email = userAccountDto.getEmail();
//        String questionCode = userAccountDto.getQuestionCode();
//        String questionAnswer = userAccountDto.getQuestionAnswer();
//        String randNum =  numberGen.four_digits(12, 1);
//
//        try{
//            Boolean result = emailQuestionCheckService.emailQuestionCheck(email,questionCode, questionAnswer, randNum);
//            resultMap.put("emailPresent", true);
//            resultMap.put("match", result);
//            resultMap.put("error", null);
//        }
//        catch(Exception ex){
//            resultMap.put("emailPresent", null);
//            resultMap.put("match", false);
//            resultMap.put("error", ex.toString());
//            System.out.println(ex);
//        }
//        return resultMap;
//    }
//
//    @PostMapping(value = "/resetCheckEmailPW")
//    //중복검사 하는 것으로 만들어야 한다.
//    public HashMap<String, Object> ResetCheckPW(@RequestBody UserAccountDto userAccountDto) throws JsonProcessingException {
//        HashMap<String, Object> resultMap = new HashMap();
//
//        String email = userAccountDto.getEmail();
//        String password = userAccountDto.getPassword();
//
//        try{
//            Boolean result = loginService.Login(email, password);
//            resultMap.put("match", result);
//            resultMap.put("error", null);
//        }
//        catch(Exception ex){
//            resultMap.put("match", false);
//            resultMap.put("error", ex.toString());
//            System.out.println(ex);
//        }
//        return resultMap;
//    }
//
//    @PostMapping(value = "/resetPassword")
//    public HashMap<String, Object> ResetPassword(@RequestBody UserAccountDto userAccountDto) throws JsonProcessingException {
//
//        HashMap<String, Object> resultMap = new HashMap();
//
//        String email = userAccountDto.getEmail();
//        String password = userAccountDto.getPassword();
//
//        try{
//            Boolean result = resetPasswordService.ResetPassword(email, password);
//            resultMap.put("updated", result);
//            resultMap.put("error", null);
//        }
//        catch(Exception ex){
////            resultMap.put("verificationNum", randNum);
//            resultMap.put("updated", false);
//            resultMap.put("error", ex.toString());
//            System.out.println(ex);
//        }
//        return resultMap;
//    }
//
//    @PostMapping(value = "/resetUid")
//    //중복검사 하는 것으로 만들어야 한다.
//    public HashMap<String, Object> ResetUid(@RequestBody UserAccountDto userAccountDto) throws JsonProcessingException {
//        HashMap<String, Object> resultMap = new HashMap();
//
//        String email = userAccountDto.getEmail();
//        NumberGen numberGen = new NumberGen();
//        String randNum = numberGen.four_digits(10,1);
//
//        try{
//            Boolean result = resetUidService.ResetUid(email, randNum);
//            resultMap.put("updated", result);
//            resultMap.put("uid", randNum);
//            resultMap.put("error", null);
//        }
//        catch(Exception ex){
////            resultMap.put("verificationNum", randNum);
//            resultMap.put("updated", false);
//            resultMap.put("uid", randNum);
//            resultMap.put("error", ex.toString());
//            System.out.println(ex);
//        }
//        return resultMap;
//    }
//
//    @PostMapping(value = "/resetShapeCode")
//    //중복검사 하는 것으로 만들어야 한다.
//    public HashMap<String, Object> ResetShapeCode(@RequestBody UserAccountDto userAccountDto) throws JsonProcessingException {
//        HashMap<String, Object> resultMap = new HashMap();
//
//        String email = userAccountDto.getEmail();
//        String shapeCode = userAccountDto.getShapeCode();
//
//        try{
//            Boolean result = resetShapeCodeService.ResetShapeCode(email, shapeCode);
//            resultMap.put("updated", result);
//            resultMap.put("error", null);
//        }
//        catch(Exception ex){
////            resultMap.put("verificationNum", randNum);
//            resultMap.put("updated", false);
//            resultMap.put("error", ex.toString());
//            System.out.println(ex);
//        }
//        return resultMap;
//    }
//
//    @PostMapping(value = "/resetCheckPhone")
//    //중복검사 하는 것으로 만들어야 한다.
//    public HashMap<String, Object> ResetCheckPhone(@RequestBody UserAccountDto userAccountDto) throws JsonProcessingException {
//        HashMap<String, Object> resultMap = new HashMap();
//        HashMap<String, Object> tmpMap = new HashMap();
//        NumberGen numberGen = new NumberGen();
//        String[] numbers = {"99999999999"};
//        String randNum =  numberGen.four_digits(4, 1);
//        String phone = userAccountDto.getPhone();
//        ObjectMapper mapper = new ObjectMapper();
//        SendMsgService_New sendMsgService_new = new SendMsgService_New();
//
//        try{
//            UserAccountVw result = userAccountVWRepository.findByLoginVwPhone(phone);
//            if(result.getLoginVwEmail() == userAccountDto.getEmail()){
//                resultMap.put("error", "input phone number is already used by current owner: no need to change");
//            }
//            else{
//                resultMap.put("error", "cannot use input phone number due to [phonePresent = true]");
//            }
//            resultMap.put("verificationNum", randNum);
//            resultMap.put("phonePresent", true);
//        }
//        catch(Exception ex){
//            numbers[0] = phone;
//            tmpMap.put("type", "SMS");
//            tmpMap.put("from", "01050055438");
//            tmpMap.put("to", numbers);
//            tmpMap.put("content", "인증번호 [" + randNum + "] 숫자 4자리를 입력해주세요 - 파워로그 모바일");
//            String json = mapper.writeValueAsString(tmpMap);
//
//            sendMsgService_new.NewSend("https://api-sens.ncloud.com/v1/sms/services/ncp:sms:kr:258080742855:testpowerlog/messages", json);
//
//            resultMap.put("verificationNum", randNum);
//            resultMap.put("phonePresent", false);
//            resultMap.put("error", "Normal when NullPointerException:" + ex.toString());
//            System.out.println(ex);
//        }
//        return resultMap;
//    }
//
//    @PostMapping(value = "/resetPhone")
//    //중복검사 하는 것으로 만들어야 한다.
//    public HashMap<String, Object> ResetPhone(@RequestBody UserAccountDto userAccountDto) throws JsonProcessingException {
//        HashMap<String, Object> resultMap = new HashMap();
//
//        String email = userAccountDto.getEmail();
//        String phone = userAccountDto.getPhone();
//
//        try{
//            Boolean result = resetPhoneService.ResetPhone(email, phone);
//            resultMap.put("updated", result);
//            resultMap.put("error", null);
//        }
//        catch(Exception ex){
////            resultMap.put("verificationNum", randNum);
//            resultMap.put("updated", false);
//            resultMap.put("error", ex.toString());
//            System.out.println(ex);
//        }
//        return resultMap;
//    }
//
//    @PostMapping(value = "/deleteAccount")
//    //중복검사 하는 것으로 만들어야 한다.
//    public HashMap<String, Object> DeleteAccount(@RequestBody UserAccountDto userAccountDto) throws JsonProcessingException {
//        HashMap<String, Object> resultMap = new HashMap();
//
//        String email = userAccountDto.getEmail();
//        String password = userAccountDto.getPassword();
//
//        Boolean result = loginService.Login(email, password);
//        try{
//            if(result){
//                deleteAccountService.DeleteAccount(email);
//
//            }
//            resultMap.put("emailPresent", userAccountVWRepository.findById(email));
//            resultMap.put("match", result);
//            resultMap.put("error", null);
//        }
//        catch(Exception ex){
////            resultMap.put("verificationNum", randNum);
//            resultMap.put("emailPresent", false);
//            resultMap.put("match", result);
//            resultMap.put("error", ex.toString());
//            System.out.println(ex);
//        }
//        return resultMap;
//    }
}
