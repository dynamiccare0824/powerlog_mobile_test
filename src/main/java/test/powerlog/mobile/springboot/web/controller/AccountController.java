package test.powerlog.mobile.springboot.web.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import test.powerlog.mobile.springboot.domain.view.LogLateMsrVw;
import test.powerlog.mobile.springboot.domain.view.LogLateMsrVwRepository;
import test.powerlog.mobile.springboot.domain.view.UserAccountVw;
import test.powerlog.mobile.springboot.domain.view.UserAccountVwRepository;
import test.powerlog.mobile.springboot.service.*;
import test.powerlog.mobile.springboot.web.dto.SignUpDto;
import test.powerlog.mobile.springboot.web.dto.request.*;
import test.powerlog.mobile.springboot.web.dto.response.BooleanResponseDto;
import test.powerlog.mobile.springboot.web.dto.response.ListResult;
import test.powerlog.mobile.springboot.web.dto.response.SingleResult;

import java.time.LocalDateTime;
import java.util.HashMap;

@Api(tags = {"1. UserAccount"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private UserAccountVwRepository userAccountVWRepository;

    @Autowired
    private LogLateMsrVwRepository logLateMsrVwRepository;

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
    UpdatePhoneService updatePhoneService;

    @Autowired
    DeleteAccountService deleteAccountService;

    @Autowired
    ResponseService responseService;

    @Autowired
    EmailService emailService;

    @Autowired
    SendMsgService_New sendMsgService_new;

    @Autowired
    NumberGen numberGen;

    @ApiOperation(value = "회원 로그인", notes = "이메일 아이디와 비밀번호를 받아 로그인한다!")
    @PostMapping(value = "/login")
    public ListResult<LogLateMsrVw>Login(@RequestBody RequestEmailPwDto requestEmailPwDto) throws Exception {
        HashMap<String, Object> resultMap = new HashMap();
//        LogLateMsrDto logLateMsrDto = new LogLateMsrDto();
        String email = requestEmailPwDto.getEmail();
        String password = requestEmailPwDto.getPassword();
        try{
            //아이디(이메일)이 존재하지 않는 경우 여기서 catch로 넘어가게 될 것임
            Boolean result = loginService.Login(email, password);
            return responseService.getListResult(logLateMsrVwRepository.findAllByLgLateMsrVwEmail(email));
        }
        // 아이디가 아예 존재하지 않는 경우
        catch(Exception ex){
            return responseService.getListResult(logLateMsrVwRepository.findAllByLgLateMsrVwEmail(email));
        }
    }

    @ApiOperation(value = "이메일 중복 검사 (現)IsEmailPresent  (前)SignUpCheckEmail", notes = "회원가입 시, 이메일 아이디가 DB에 이미 존재하는지 여부를 검사한다.")
    @PostMapping(value = "signup/dupcheck/email")
    public SingleResult<BooleanResponseDto> DupCheckEmail(@RequestBody AccountRequestDto accountRequestDto) throws JsonProcessingException {
        String email = accountRequestDto.getEmail();
        if(userAccountVWRepository.findById(email).isPresent()) {
            BooleanResponseDto isPresentDto = BooleanResponseDto.builder().emailPresent(true).build();
            return responseService.getSingleResult(isPresentDto);
        }
        else{
            BooleanResponseDto isPresentDto = BooleanResponseDto.builder().emailPresent(false).build();
            return responseService.getSingleResult(isPresentDto);            }
    }

    @PostMapping(value = "signup/dupcheck/sendmsg")
    public HashMap<String, Object> DupCheckSendMsg(@RequestBody UserAccountDto userAccountDto) throws JsonProcessingException {
        HashMap<String, Object> tmpMap = new HashMap();
        HashMap<String, Object> resultMap = new HashMap();
        String phone = userAccountDto.getPhone();
        ObjectMapper mapper = new ObjectMapper();
        NumberGen numberGen = new NumberGen();
        String randNum = numberGen.Digits(4, 1);
        String[] numbers = {"99999999999"};
        try{
            userAccountVWRepository.findByLoginVwPhone(phone).getLoginVwPhone();
            resultMap.put("phonePresent", true);
            resultMap.put("verificationNum", randNum);
            resultMap.put("error", "isPresentError");
        }
        catch(Exception ex){
            System.out.println(ex);
//             전화번호가 존재하지 않은 경우에만 메시지를 보낸다

            numbers[0] = phone;
            tmpMap.put("type", "SMS");
            tmpMap.put("from", "01050055438");
            tmpMap.put("to", numbers);
            tmpMap.put("content", "인증번호 [" + randNum + "] 숫자 4자리를 입력해주세요 - 파워로그 모바일");
            String json = mapper.writeValueAsString(tmpMap);

            sendMsgService_new.NewSend("https://api-sens.ncloud.com/v1/sms/services/ncp:sms:kr:258080742855:testpowerlog/messages", json);

            resultMap.put("phonePresent", false);
            resultMap.put("verificationNum", randNum);
            resultMap.put("error", null);
        }
        return resultMap;
    }

    @PostMapping(value = "signup/register")
    public HashMap<String, Object> Register(@RequestBody UserAccountDto userAccountDto) throws JsonProcessingException {

        HashMap<String, Object> resultMap = new HashMap();
        NumberGen numberGen = new NumberGen();

        java.time.LocalDateTime localDateTime = LocalDateTime.now();
        String tmpUid = numberGen.Digits(8, 1);
        int careerY = userAccountDto.getCareerYear();
        int careerM = userAccountDto.getCareerMonth();

        System.out.println(userAccountDto.getQuestionCode());
        System.out.println(userAccountDto.getQuestionAnswer());



        SignUpDto signUpDto  = SignUpDto.builder().email(userAccountDto.getEmail()).password(userAccountDto.getPassword()).uid(tmpUid).name(userAccountDto.getName())
                .gender(userAccountDto.getGender()).birth(userAccountDto.getBirth()).height(userAccountDto.getHeight()).weight(userAccountDto.getWeight())
                .agreeFlag(userAccountDto.getAgreeFlag()).personalFlag(userAccountDto.getAgreeFlag()).shapeCode(userAccountDto.getShapeCode()).qAnswer(userAccountDto.getQuestionAnswer()).qCode(userAccountDto.getQuestionCode())
                .verification(userAccountDto.getVerification()).phone(userAccountDto.getPhone()).createdTime(localDateTime).updatedTime(localDateTime).career(careerM + careerY * 12).build();
        signUpService.Signup(signUpDto); // save 실행

        try{
            String findById = userAccountVWRepository.findById(userAccountDto.getEmail()).get().getLoginVwEmail();
            resultMap.put("findById", findById);
            resultMap.put("result", true);
            resultMap.put("error", null);
        }
        catch(Exception ex){
            resultMap.put("findById", ex);
            resultMap.put("result", false);
            resultMap.put("error", ex.toString());
        }

        return resultMap;
    }

    @PostMapping(value = "/validation/email-phone")
    public HashMap<String, Object> ValidatePhoneSendMsg(@RequestBody UserAccountDto userAccountDto) throws JsonProcessingException {

        HashMap<String, Object> resultMap = new HashMap();
        HashMap<String, Object> tmpMap = new HashMap();
        NumberGen numberGen = new NumberGen();
        ObjectMapper mapper = new ObjectMapper();

        String phone = userAccountDto.getPhone();
        String email = userAccountDto.getEmail();
        String[] numbers = {"99999999999"};
        String randNum =  numberGen.Digits(4, 1);

        try{
            Boolean result = emailPhoneCheckService.emailPhoneCheck(email, phone);
            if(result){
                numbers[0] = phone;
                tmpMap.put("type", "SMS");
                tmpMap.put("from", "01050055438");
                tmpMap.put("to", numbers);
                tmpMap.put("content", "인증번호 [" + randNum + "] 숫자 4자리를 입력해주세요 - 파워로그 모바일");
                String json = mapper.writeValueAsString(tmpMap);

                sendMsgService_new.NewSend("https://api-sens.ncloud.com/v1/sms/services/ncp:sms:kr:258080742855:testpowerlog/messages", json);
            }
            resultMap.put("verificationNum", randNum);
            resultMap.put("match", result);
            resultMap.put("error", null);
        }
        catch(Exception ex){
            resultMap.put("verificationNum", randNum);
            resultMap.put("match", false);
            resultMap.put("error", ex.toString());
            System.out.println(ex);
        }
        return resultMap;
    }

    @PostMapping(value = "/validation/email-question")
    public HashMap<String, Object> ValidateQuestionSendMail(@RequestBody UserAccountDto userAccountDto) throws JsonProcessingException {

        HashMap<String, Object> resultMap = new HashMap();

        String email = userAccountDto.getEmail();
        String questionCode = userAccountDto.getQuestionCode();
        String questionAnswer = userAccountDto.getQuestionAnswer();
        String randNum =  numberGen.Digits(12, 1);

        try{
            Boolean result = emailQuestionCheckService.emailQuestionCheck(email,questionCode, questionAnswer, randNum);
            resultMap.put("emailPresent", true);
            resultMap.put("match", result);
            resultMap.put("error", null);
        }
        catch(Exception ex){
            resultMap.put("emailPresent", null);
            resultMap.put("match", false);
            resultMap.put("error", ex.toString());
            System.out.println(ex);
        }
        return resultMap;
    }

    @PostMapping(value = "/validation/email-password")
    public HashMap<String, Object> ValidateAccount(@RequestBody ResetDto resetDto) throws JsonProcessingException {
        HashMap<String, Object> resultMap = new HashMap();

        String email = resetDto.getEmail();
        String password = resetDto.getPassword();

        try{
            Boolean result = loginService.Login(email, password);
            resultMap.put("match", result);
            resultMap.put("error", null);
        }
        catch(Exception ex){
            resultMap.put("match", false);
            resultMap.put("error", ex.toString());
            System.out.println(ex);
        }
        return resultMap;
    }

    @PostMapping(value = "/update/newpassword")
    public HashMap<String, Object> UpdatePassword(@RequestBody ResetDto resetDto) throws JsonProcessingException {

        HashMap<String, Object> resultMap = new HashMap();

        String email = resetDto.getEmail();
        String password = resetDto.getPassword();

        try{
            System.out.println(password + "1");
            Boolean result = resetPasswordService.ResetPassword(email, password);
            resultMap.put("updated", result);
            resultMap.put("error", null);
        }
        catch(Exception ex){
//            resultMap.put("verificationNum", randNum);
            resultMap.put("updated", false);
            resultMap.put("error", ex.toString());
            System.out.println(ex);
        }
        return resultMap;
    }

    @PostMapping(value = "/update/newuid")
    //중복검사 하는 것으로 만들어야 한다.
    public HashMap<String, Object> UpdateUid(@RequestBody ResetDto resetDto) throws JsonProcessingException {
        HashMap<String, Object> resultMap = new HashMap();

        String email = resetDto.getEmail();
        NumberGen numberGen = new NumberGen();
        String randNum = numberGen.ComplicatedDigits(12,1);

        try{
            Boolean result = resetUidService.ResetUid(email, randNum);
            resultMap.put("updated", result);
            resultMap.put("uid", randNum);
            resultMap.put("error", null);
        }
        catch(Exception ex){
//            resultMap.put("verificationNum", randNum);
            resultMap.put("updated", false);
            resultMap.put("uid", randNum);
            resultMap.put("error", ex.toString());
            System.out.println(ex);
        }
        return resultMap;
    }

    @PostMapping(value = "/update/newshapecode")
    public HashMap<String, Object> UpdateShapeCode(@RequestBody ResetDto resetDto) throws JsonProcessingException {
        HashMap<String, Object> resultMap = new HashMap();

        String email = resetDto.getEmail();
        String shapeCode = resetDto.getShapeCode();

        try{
            Boolean result = resetShapeCodeService.ResetShapeCode(email, shapeCode);
            resultMap.put("updated", result);
            resultMap.put("error", null);
        }
        catch(Exception ex){
//            resultMap.put("verificationNum", randNum);
            resultMap.put("updated", false);
            resultMap.put("error", ex.toString());
            System.out.println(ex);
        }
        return resultMap;
    }

    @PostMapping(value = "/update/validation/newphone")
    public HashMap<String, Object> UpdateValidatePhone(@RequestBody ResetDto resetDto) throws JsonProcessingException {
        HashMap<String, Object> resultMap = new HashMap();
        HashMap<String, Object> tmpMap = new HashMap();
        NumberGen numberGen = new NumberGen();
        String[] numbers = {"99999999999"};
        String randNum =  numberGen.Digits(4, 1);
        String phone = resetDto.getPhone();
        ObjectMapper mapper = new ObjectMapper();
        SendMsgService_New sendMsgService_new = new SendMsgService_New();

        try{
            UserAccountVw result = userAccountVWRepository.findByLoginVwPhone(phone);
            if(result.getLoginVwEmail() == resetDto.getEmail()){
                resultMap.put("error", "input phone number is already used by current owner: no need to change");
            }
            else{
                resultMap.put("error", "cannot use input phone number due to [phonePresent = true]");
            }
            resultMap.put("verificationNum", randNum);
            resultMap.put("phonePresent", true);
        }
        catch(Exception ex){
            numbers[0] = phone;
            tmpMap.put("type", "SMS");
            tmpMap.put("from", "01050055438");
            tmpMap.put("to", numbers);
            tmpMap.put("content", "인증번호 [" + randNum + "] 숫자 4자리를 입력해주세요 - 파워로그 모바일");
            String json = mapper.writeValueAsString(tmpMap);

            sendMsgService_new.NewSend("https://api-sens.ncloud.com/v1/sms/services/ncp:sms:kr:258080742855:testpowerlog/messages", json);

            resultMap.put("verificationNum", randNum);
            resultMap.put("phonePresent", false);
            resultMap.put("error", "Normal when NullPointerException:" + ex.toString());
            System.out.println(ex);
        }
        return resultMap;
    }

    @PostMapping(value = "/update/newphone")
    public HashMap<String, Object> UpdatePhone(@RequestBody ResetDto resetDto) throws JsonProcessingException {
        HashMap<String, Object> resultMap = new HashMap();
        String email = resetDto.getEmail();
        String phone = resetDto.getPhone();
        try{
            Boolean result = updatePhoneService.UpdatePhone(email, phone);
            resultMap.put("updated", result);
            resultMap.put("error", null);
        }
        catch(Exception ex){
//            resultMap.put("verificationNum", randNum);
            resultMap.put("updated", false);
            resultMap.put("error", ex.toString());
            System.out.println(ex);
        }
        return resultMap;
    }

    @PostMapping(value = "/delete/user")
    public HashMap<String, Object> DeleteUser(@RequestBody ResetDto resetDto) throws JsonProcessingException {
        HashMap<String, Object> resultMap = new HashMap();

        String email = resetDto.getEmail();
        String password = resetDto.getPassword();

        Boolean result = loginService.Login(email, password);
        try{
            if(result){
                deleteAccountService.DeleteAccount(email);

            }
            resultMap.put("emailPresent", userAccountVWRepository.findById(email));
            resultMap.put("match", result);
            resultMap.put("error", null);
        }
        catch(Exception ex){
//            resultMap.put("verificationNum", randNum);
            resultMap.put("emailPresent", false);
            resultMap.put("match", result);
            resultMap.put("error", ex.toString());
            System.out.println(ex);
        }
        return resultMap;
    }
}
