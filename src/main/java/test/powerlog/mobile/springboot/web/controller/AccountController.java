package test.powerlog.mobile.springboot.web.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import test.powerlog.mobile.springboot.domain.view.*;
import test.powerlog.mobile.springboot.service.common.CommonResponseService;
import test.powerlog.mobile.springboot.service.mobile.*;
import test.powerlog.mobile.springboot.web.dto.mobile.request.*;
import test.powerlog.mobile.springboot.web.dto.mobile.response.RspDupCheckEmailDto;
import test.powerlog.mobile.springboot.web.dto.mobile.response.RspDupCheckSendMsg;
import test.powerlog.mobile.springboot.web.dto.mobile.response.RspLoginDto;

import javax.validation.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Api(tags = {"1. UserAccount"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/account")
public class AccountController {

    //Repository
    @Autowired
    private UserAccountVwRepository userAccountVWRepository;

    @Autowired
    private LogLateMsrVwRepository logLateMsrVwRepository;

    @Autowired
    private WorkoutCodeVwRepository workoutCodeVwRepository;

    // Service
    @Autowired
    private LoginService loginService;

    @Autowired
    private EmailPhoneCheckService emailPhoneCheckService;

    @Autowired
    private EmailQuestionCheckService emailQuestionCheckService;

    @Autowired
    private SignUpService signUpService;

    @Autowired
    private ResetPasswordService resetPasswordService;

    @Autowired
    private ResetUidService resetUidService;

    @Autowired
    private ResetShapeCodeService resetShapeCodeService;

    @Autowired
    private UpdatePhoneService updatePhoneService;

    @Autowired
    private DeleteAccountService deleteAccountService;

    @Autowired
    private CommonResponseService commonResponseService;

    @Autowired
    private DupCheckEmailService dupCheckEmailService;

    @Autowired
    private DupCheckPhoneService dupCheckPhoneService;

    @Autowired
    private SendMsgService sendMsgService;

    @Autowired
    private WorkoutCodeService workoutCodeService;

    @Autowired
    private NumberGenService numberGenService;

    @Autowired
    private SendEmailService sendEmailService;

    //Completed
    @ApiOperation(value = "회원 로그인", notes = "이메일 아이디와 비밀번호를 받아 로그인한다!")
    @PostMapping(value = "/login")
    public RspLoginDto<LogLateMsrVw> Login(@RequestBody @Valid ReqLoginDto reqLoginDto, BindingResult bindingResult) throws Exception {
        HashMap<String, Object> resultMap;
        HashMap<String, Object> logRecordFormMap = workoutCodeService.WorkoutCodeVwMap();
        // 파라미터 에러가 발생했다면
        if (bindingResult.hasErrors()) {
            StringBuffer errorString = new StringBuffer();
            List<ObjectError> inValidParamList = bindingResult.getAllErrors();
            // 파라미터 오류 정보만 있고, map도 없을 것이고, 받아온 리스트도 없을 것
            return commonResponseService.getRspLoginDto(inValidParamList, null, null, null);
        } else {
            // 파라미터 에러가 없으면 초기화
            String email = reqLoginDto.getEmail();
            String password = reqLoginDto.getPassword();

            // 파라미터를 가지고 resultMap 초기화
            resultMap = loginService.EmailPasswordCheck(email, password);

            //로그인 정보가 맞다면
            if ((Boolean) resultMap.get("isMatch") == true) {
                HashMap<String, Object> logRecordMap = loginService.LgLateMsrVwEmailMap(email, logRecordFormMap);
                return commonResponseService.getRspLoginDto(null, logRecordMap, logRecordFormMap, resultMap);
            }
            return commonResponseService.getRspLoginDto(null, null, logRecordFormMap, resultMap);
        }
    }

//Completed
@ApiOperation(value = "이메일 중복 검사", notes = "회원가입 시, 이메일 아이디가 DB에 이미 존재하는지 여부를 검사한다.")
@PostMapping(value = "signup/dupcheck/email")
public RspDupCheckEmailDto DupCheckEmail(@RequestBody @Valid ReqDupCheckEmailDto reqDupCheckEmailDto,BindingResult bindingResult)throws JsonProcessingException{
        if(bindingResult.hasErrors()){
        StringBuffer errorString=new StringBuffer();
        List<ObjectError> invalidParamList=bindingResult.getAllErrors();
        // 파라미터 오류 정보만 있고, map도 없을 것
        return commonResponseService.getRspDupCheckEmailDto(invalidParamList,null);
        }else{
        String email=reqDupCheckEmailDto.getEmail();
        HashMap<String, Object> resultMap=dupCheckEmailService.DupCheckEmail(email);
        return commonResponseService.getRspDupCheckEmailDto(null,resultMap);
        }
        }

//completed
@ApiOperation(value = "핸드폰 중복 검사 + 조건 충족 시 인증번호 발송",
        notes = "회원가입 시, 핸드폰 번호가 DB에 이미 존재하는지 여부를 검사하고 없다면 인증번호 4자리를 포함한 메시지를 발송한다.")
@PostMapping(value = "signup/dupcheck/sendmsg")
public RspDupCheckSendMsg DupCheckSendMsg(@RequestBody @Valid ReqDupCheckSendMsgDto reqDupCheckSendMsgDto,BindingResult bindingResult)throws JsonProcessingException{
        HashMap<String, Object> tmpMap=new HashMap<>();

        if(bindingResult.hasErrors()){
        StringBuffer errorString=new StringBuffer();
        List<ObjectError> invalidParamList=bindingResult.getAllErrors();
        return commonResponseService.getRspDupCheckSendMsgDto(invalidParamList,tmpMap);
        }else{
        String phone=reqDupCheckSendMsgDto.getPhone();
        NumberGenService numberGenService=new NumberGenService();
        String randNum=numberGenService.Digits(4,1);
        tmpMap=dupCheckPhoneService.DupCheckPhone(phone);
        //핸드폰 번호가 DB에 없다면
        if(!(Boolean)tmpMap.get("phonePresent")){
        try{
        String sendMsgResult=sendMsgService.buildJsonSendMsg(phone,randNum,tmpMap);
        tmpMap.put("verificationNum",randNum);
        tmpMap.put("sendMsgResult",sendMsgResult);
        }
        //핸드폰 번호가 DB에 없는 것은 맞는데 서버 오류로 문자가 보내지지 않는 문제가 발생했다면
        catch(Exception ex){
        tmpMap.replace("error",ex.toString());
        return commonResponseService.getRspDupCheckSendMsgDto(null,tmpMap);
        }
        }
        //핸드폰 번호가 DB에 있다면
        else{
        return commonResponseService.getRspDupCheckSendMsgDto(null,tmpMap);
        }
        }
        //핸드폰 번호가 DB에 없고 문자도 잘 보내졌다면
        return commonResponseService.getRspDupCheckSendMsgDto(null,tmpMap);
        }

@PostMapping(value = "signup/register")
public HashMap<String, Object> Register(@RequestBody UserAccountDto userAccountDto)throws JsonProcessingException{

        HashMap<String, Object> resultMap=new HashMap();
        NumberGenService numberGenService=new NumberGenService();

        java.time.LocalDateTime localDateTime=LocalDateTime.now();
        String tmpUid=numberGenService.Digits(12,1);
        int careerY=userAccountDto.getCareerYear();
        int careerM=userAccountDto.getCareerMonth();

        System.out.println(userAccountDto.getQuestionCode());
        System.out.println(userAccountDto.getQuestionAnswer());


        SignUpDto signUpDto=SignUpDto.builder().email(userAccountDto.getEmail()).password(userAccountDto.getPassword()).uid(tmpUid).name(userAccountDto.getName())
        .gender(userAccountDto.getGender()).birth(userAccountDto.getBirth()).height(userAccountDto.getHeight()).weight(userAccountDto.getWeight())
        .agreeFlag(userAccountDto.getAgreeFlag()).personalFlag(userAccountDto.getAgreeFlag()).shapeCode(userAccountDto.getShapeCode()).qAnswer(userAccountDto.getQuestionAnswer()).qCode(userAccountDto.getQuestionCode())
        .verification(userAccountDto.getVerification()).phone(userAccountDto.getPhone()).createdTime(localDateTime).updatedTime(localDateTime).career(careerM+careerY*12).build();
        signUpService.Signup(signUpDto); // save 실행

        try{
        String findById=userAccountVWRepository.findById(userAccountDto.getEmail()).get().getLoginVwEmail();
        resultMap.put("findById",findById);
        resultMap.put("result",true);
        resultMap.put("error",null);
        }catch(Exception ex){
        resultMap.put("findById",ex);
        resultMap.put("result",false);
        resultMap.put("error",ex.toString());
        }

        return resultMap;
        }

//    @PostMapping(value = "/validation/email-phone")
//    public HashMap<String, Object> ValidatePhoneSendMsg(@RequestBody ReqEmailPasswordCheckDto emailPasswordCheckDto) throws JsonProcessingException {
//
//
//        if(emailPasswordCheckResultMap.get("error") != null && (Boolean) emailPasswordCheckResultMap.get("result")){
//            try{
//                return responseService.getRspLoginDto(logLateMsrVwRepository.findAllByLgLateMsrVwEmail(email), resultMap);
//            }
//            // 아이디가 아예 존재하지 않는 경우
//            catch(Exception ex){
//                resultMap.replace("error", ex.toString());
//                return responseService.getRspLoginDto(null, resultMap);
//            }
//        }
//        else{
//            return responseService.getRspLoginDto(null, resultMap);
//        }
//
//        HashMap<String, Object> resultMap = new HashMap();
//        HashMap<String, Object> tmpMap = new HashMap();
//        NumberGenService numberGenService = new NumberGenService();
//        ObjectMapper mapper = new ObjectMapper();
//
//        String phone = userAccountDto.getPhone();
//        String email = userAccountDto.getEmail();
//        String[] numbers = {"99999999999"};
//        String randNum =  numberGenService.Digits(4, 1);
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

//    @PostMapping(value = "/validation/email-question")
//    public HashMap<String, Object> ValidateQuestionSendMail(@RequestBody UserAccountDto userAccountDto) throws JsonProcessingException {
//
//        HashMap<String, Object> resultMap = new HashMap();
//
//        String email = userAccountDto.getEmail();
//        String questionCode = userAccountDto.getQuestionCode();
//        String questionAnswer = userAccountDto.getQuestionAnswer();
//        String randNum = numberGenService.Digits(12, 1);
//
//        try {
//            Boolean result = emailQuestionCheckService.emailQuestionCheck(email, questionCode, questionAnswer, randNum);
//            resultMap.put("emailPresent", true);
//            resultMap.put("match", result);
//            resultMap.put("error", null);
//        } catch (Exception ex) {
//            resultMap.put("emailPresent", null);
//            resultMap.put("match", false);
//            resultMap.put("error", ex.toString());
//            System.out.println(ex);
//        }
//        return resultMap;
//    }

//    @ApiOperation(value = "계정 본인 확인",
//            notes = "더보기 메뉴에서 계정 본인 확인을 위해서 비밀번호 재검사, 이메일과 패스워드 isMatch 체크하여 리턴한다.")
//    @PostMapping(value = "/validation/email-password")
//    public RspEmailPasswordCheckDto ValidateAccount(@RequestBody ReqEmailPasswordCheckDto emailPasswordCheckDto) throws JsonProcessingException {
//        String email = emailPasswordCheckDto.getEmail();
//        String password = emailPasswordCheckDto.getPassword();
//        HashMap checkResultMap = emailPasswordCheckService.EmailPasswordCheck(email, password);
//        return responseService.getRspEmailPasswordCheckDto(checkResultMap);
//    }
//
//    @PostMapping(value = "/update/newpassword")
//    public HashMap<String, Object> UpdatePassword(@RequestBody ResetDto resetDto) throws JsonProcessingException {
//
//        HashMap<String, Object> resultMap = new HashMap();
//
//        String email = resetDto.getEmail();
//        String password = resetDto.getPassword();
//
//        try {
//            System.out.println(password + "1");
//            Boolean result = resetPasswordService.ResetPassword(email, password);
//            resultMap.put("updated", result);
//            resultMap.put("error", null);
//        } catch (Exception ex) {
////            resultMap.put("verificationNum", randNum);
//            resultMap.put("updated", false);
//            resultMap.put("error", ex.toString());
//            System.out.println(ex);
//        }
//        return resultMap;
//    }
//
//    @PostMapping(value = "/update/newuid")
//    //중복검사 하는 것으로 만들어야 한다.
//    public HashMap<String, Object> UpdateUid(@RequestBody ResetDto resetDto) throws JsonProcessingException {
//        HashMap<String, Object> resultMap = new HashMap();
//
//        String email = resetDto.getEmail();
//        NumberGenService numberGenService = new NumberGenService();
//        String randNum = numberGenService.ComplicatedDigits(12, 1);
//
//        try {
//            Boolean result = resetUidService.ResetUid(email, randNum);
//            resultMap.put("updated", result);
//            resultMap.put("uid", randNum);
//            resultMap.put("error", null);
//        } catch (Exception ex) {
////            resultMap.put("verificationNum", randNum);
//            resultMap.put("updated", false);
//            resultMap.put("uid", randNum);
//            resultMap.put("error", ex.toString());
//            System.out.println(ex);
//        }
//        return resultMap;
//    }
//
//    @PostMapping(value = "/update/newshapecode")
//    public HashMap<String, Object> UpdateShapeCode(@RequestBody ResetDto resetDto) throws JsonProcessingException {
//        HashMap<String, Object> resultMap = new HashMap();
//
//        String email = resetDto.getEmail();
//        String shapeCode = resetDto.getShapeCode();
//
//        try {
//            Boolean result = resetShapeCodeService.ResetShapeCode(email, shapeCode);
//            resultMap.put("updated", result);
//            resultMap.put("error", null);
//        } catch (Exception ex) {
////            resultMap.put("verificationNum", randNum);
//            resultMap.put("updated", false);
//            resultMap.put("error", ex.toString());
//            System.out.println(ex);
//        }
//        return resultMap;
//    }

//    @PostMapping(value = "/update/validation/newphone")
//    public HashMap<String, Object> UpdateValidatePhone(@RequestBody ResetDto resetDto) throws JsonProcessingException {
//        HashMap<String, Object> resultMap = new HashMap();
//        HashMap<String, Object> tmpMap = new HashMap();
//        NumberGenService numberGenService = new NumberGenService();
//        String[] numbers = {"99999999999"};
//        String randNum =  numberGenService.Digits(4, 1);
//        String phone = resetDto.getPhone();
//        ObjectMapper mapper = new ObjectMapper();
//        SendMsgService_New sendMsgService_new = new SendMsgService_New();
//
//        try{
//            UserAccountVw result = userAccountVWRepository.findByLoginVwPhone(phone);
//            if(result.getLoginVwEmail() == resetDto.getEmail()){
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

//    @PostMapping(value = "/update/newphone")
//    public HashMap<String, Object> UpdatePhone(@RequestBody ResetDto resetDto) throws JsonProcessingException {
//        HashMap<String, Object> resultMap = new HashMap();
//        String email = resetDto.getEmail();
//        String phone = resetDto.getPhone();
//        try {
//            Boolean result = updatePhoneService.UpdatePhone(email, phone);
//            resultMap.put("updated", result);
//            resultMap.put("error", null);
//        } catch (Exception ex) {
////            resultMap.put("verificationNum", randNum);
//            resultMap.put("updated", false);
//            resultMap.put("error", ex.toString());
//            System.out.println(ex);
//        }
//        return resultMap;
//    }

//    @ApiOperation(value = "회원 탈퇴", notes = "회원 정보를 UserTb에서 찾아 삭제한다")
//    @PostMapping(value = "/delete/user")
//    public RspDeleteUserDto<Object> DeleteUser(@RequestBody ReqDeleteUserDto reqDeleteUserDto) throws JsonProcessingException {
//
//        String email = reqDeleteUserDto.getEmail();
//        String password = reqDeleteUserDto.getPassword();
//
//        HashMap<String, Object> checkResultMap = emailPasswordCheckService.EmailPasswordCheck(email, password);
//        if (checkResultMap.get("error") == null) {
//            HashMap<String, Object> deleteResultMap = deleteAccountService.DeleteAccount(checkResultMap, email);
//            return responseService.getRspDeleteUserDto(deleteResultMap);
//        } else {
//            return responseService.getRspDeleteUserDto(checkResultMap);
//        }
//    }
        }

