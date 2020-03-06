package test.powerlog.mobile.springboot.web.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import test.powerlog.mobile.springboot.domain.view.*;
import test.powerlog.mobile.springboot.service.common.CommonResponseService;
import test.powerlog.mobile.springboot.service.mobile.*;
import test.powerlog.mobile.springboot.service.mobile.old.*;
import test.powerlog.mobile.springboot.web.dto.common.CommonResponseDto;
import test.powerlog.mobile.springboot.web.dto.mobile.request.*;
import test.powerlog.mobile.springboot.web.dto.mobile.request.account.ReqDupCheckBodyDto;
import test.powerlog.mobile.springboot.web.dto.mobile.response.*;

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

    @Autowired
    private AccountService accountService;

    @Autowired
    private CheckService checkService;

    //Completed
    //회원정보 틀렸을 때 오류
    @ApiOperation(value = "회원 로그인", notes = "이메일 아이디와 비밀번호를 받아 로그인한다!")
    @PostMapping(value = "/login")
    public RspLoginDto<LogLateMsrVw> Login(@RequestBody @Valid ReqLoginDto reqLoginDto, BindingResult bindingResult) throws Exception {
        HashMap<String, Object> resultMap;
        HashMap<String, Object> logRecordFormMap = workoutCodeService.WorkoutCodeVwLoginMap();
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
            resultMap = accountService.EmailPasswordCheck(email, password);

            //로그인 정보가 맞든 틀리든.. formMap이 있어서 틀리면 그걸 활용해
            HashMap<String, Object> logRecordMap = accountService.LgLateMsrVwEmailMap(email, logRecordFormMap);
            return commonResponseService.getRspLoginDto(null, logRecordMap, logRecordFormMap, resultMap);
        }
    }

    //Completed
    @ApiOperation(value = "이메일 중복 검사", notes = "회원가입 시, 이메일 아이디가 DB에 이미 존재하는지 여부를 검사한다.")
    @PostMapping(value = "signup/dupcheck/email")
    public RspDupCheckEmailDto DupCheckEmail(@RequestBody @Valid ReqDupCheckEmailDto reqDupCheckEmailDto, BindingResult bindingResult) throws JsonProcessingException {
        if (bindingResult.hasErrors()) {
            StringBuffer errorString = new StringBuffer();
            List<ObjectError> invalidParamList = bindingResult.getAllErrors();
            // 파라미터 오류 정보만 있고, map도 없을 것
            return commonResponseService.getRspDupCheckEmailDto(invalidParamList, null);
        } else {
            String email = reqDupCheckEmailDto.getEmail();
            HashMap<String, Object> resultMap = checkService.DupCheckEmail(email);
            return commonResponseService.getRspDupCheckEmailDto(null, resultMap);
        }
    }

    //completed
    @ApiOperation(value = "핸드폰 중복 검사 + 조건 충족 시 인증번호 발송",
            notes = "회원가입 시, 핸드폰 번호가 DB에 이미 존재하는지 여부를 검사하고 없다면 인증번호 4자리를 포함한 메시지를 발송한다.")
    @PostMapping(value = "signup/dupcheck/sendmsg")
    public RspDupCheckSendMsg DupCheckSendMsg(@RequestBody @Valid ReqDupCheckSendMsgDto reqDupCheckSendMsgDto, BindingResult bindingResult) throws JsonProcessingException {
        HashMap<String, Object> tmpMap = new HashMap<>();

        if (bindingResult.hasErrors()) {
            StringBuffer errorString = new StringBuffer();
            List<ObjectError> invalidParamList = bindingResult.getAllErrors();
            return commonResponseService.getRspDupCheckSendMsgDto(invalidParamList, tmpMap);
        } else {
            String phone = reqDupCheckSendMsgDto.getPhone();
            NumberGenService numberGenService = new NumberGenService();
            String randNum = numberGenService.Digits(4, 1);
            tmpMap = checkService.DupCheckPhone(phone);
            //핸드폰 번호가 DB에 없다면
            if (!(Boolean) tmpMap.get("phonePresent")) {
                try {
                    String sendMsgResult = sendMsgService.buildJsonSendMsg(phone, randNum, tmpMap);
                    tmpMap.put("verificationNum", randNum);
                    tmpMap.put("sendMsgResult", sendMsgResult);
                }
                //핸드폰 번호가 DB에 없는 것은 맞는데 서버 오류로 문자가 보내지지 않는 문제가 발생했다면
                catch (Exception ex) {
                    tmpMap.replace("error", ex.toString());
                    return commonResponseService.getRspDupCheckSendMsgDto(null, tmpMap);
                }
            }
            //핸드폰 번호가 DB에 있다면
            else {
                return commonResponseService.getRspDupCheckSendMsgDto(null, tmpMap);
            }
        }
        //핸드폰 번호가 DB에 없고 문자도 잘 보내졌다면
        return commonResponseService.getRspDupCheckSendMsgDto(null, tmpMap);
    }

    @ApiOperation(value = "회원가입",
            notes = "회원가입")
    @PostMapping(value = "signup/register")
    public RspRegisterDto Register(@RequestBody @Valid ReqRegisterDto reqRegisterDto, BindingResult bindingResult) throws JsonProcessingException {


        HashMap<String, Object> tmpMap = new HashMap();

        if (bindingResult.hasErrors()) {
            StringBuffer errorString = new StringBuffer();
            List<ObjectError> invalidParamList = bindingResult.getAllErrors();
            return commonResponseService.getRspRegisterDto(invalidParamList, null);
        } else {
            HashMap<String, Object> resultMap = new HashMap();
            SignUpDto signUpDto = accountService.ToRegisterForm(reqRegisterDto);
            accountService.Signup(signUpDto); // save 실행
            return commonResponseService.getRspRegisterDto(null, reqRegisterDto.getEmail());
        }
    }

    @ApiOperation(value = "핸드폰 번호로 비밀번호 찾기",
            notes = "이메일과 핸드폰 번호를 이용한다")
    @PostMapping(value = "lost/validation/phone")
    public RspEmailPasswordCheckDto<Object> ValidatePhoneSendMsg(@RequestBody @Valid ReqLostValidPhoneDto reqLostValidPhoneDto, BindingResult bindingResult) throws JsonProcessingException {
        HashMap<String, Object> tmpMap = new HashMap<>();

        if (bindingResult.hasErrors()) {
            StringBuffer errorString = new StringBuffer();
            List<ObjectError> invalidParamList = bindingResult.getAllErrors();
            return commonResponseService.getRspEmailPasswordCheckDto(invalidParamList, null);
        } else {

            HashMap<String, Object> resultMap = new HashMap();
            String email = reqLostValidPhoneDto.getEmail();
            String phone = reqLostValidPhoneDto.getPhone();
            //이 이메일과 핸드폰 번호가 맞는 번호인지 체크한다
            if (checkService.emailPhoneCheck(email, phone)) {
                //맞다면 핸드폰으로 인증번호 전송
                NumberGenService numberGenService = new NumberGenService();
                String randNum = numberGenService.Digits(4,1);
                try {
                    String sendMsgResult = sendMsgService.buildJsonSendMsg(phone, randNum, tmpMap);
                    tmpMap.put("verificationNum", randNum);
                    tmpMap.put("sendMsgResult", sendMsgResult);
                }
                //핸드폰 번호가 DB에 없는 것은 맞는데 서버 오류로 문자가 보내지지 않는 문제가 발생했다면
                catch (Exception ex) {
                    tmpMap.replace("error", ex.toString());
//                    return commonResponseService.getRspDupCheckSendMsgDto(null, tmpMap);
                }
            } else {
                return null;
            }
            return null;
        }
    }

    @ApiOperation(value = "신체 정보 유효성 검사", notes = "")
    @PostMapping(value = "signup/dupcheck/bodydetail")
    public CommonResponseDto DupCheckBody(@RequestBody @Valid ReqDupCheckBodyDto reqDupCheckBodyDto, BindingResult bindingResult) throws JsonProcessingException {
        if (bindingResult.hasErrors()) {
            StringBuffer errorString = new StringBuffer();
            List<ObjectError> invalidParamList = bindingResult.getAllErrors();
            // 파라미터 오류 정보만 있고, map도 없을 것
            return commonResponseService.getCommonResponse(invalidParamList, null);
        } else {
            return commonResponseService.getCommonResponse(null, null);
        }
    }


//            // 파라미터를 가지고 resultMap 초기화
//            resultMap = accountService.EmailPasswordCheck(email, password);
//            return commonResponseService.getRspRegisterDto(null, reqRegisterDto.getEmail());
//        }
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

    @ApiOperation(value = "이메일로 비밀번호 새로 발급 받기",
            notes = "이메일과 질문답을 이용한다")
    @PostMapping(value = "lost/validation/question")
    public HashMap<String, Object> ValidateQuestionSendMail(@RequestBody UserAccountDto userAccountDto) throws JsonProcessingException {

        HashMap<String, Object> resultMap = new HashMap();

        String email = userAccountDto.getEmail();
        String questionCode = userAccountDto.getQuestionCode();
        String questionAnswer = userAccountDto.getQuestionAnswer();
        String randNum = numberGenService.Digits(12, 1);

        try {
            Boolean result = emailQuestionCheckService.emailQuestionCheck(email, questionCode, questionAnswer, randNum);
            resultMap.put("emailPresent", true);
            resultMap.put("match", result);
            resultMap.put("isError", null);
        } catch (Exception ex) {
            resultMap.put("emailPresent", null);
            resultMap.put("match", false);
            resultMap.put("isError", ex.toString());
            System.out.println(ex);
        }
        return resultMap;
    }

//    @ApiOperation(value = "계정 본인 확인",
//            notes = "더보기 메뉴에서 계정 본인 확인을 위해서 비밀번호 재검사, 이메일과 패스워드 isMatch 체크하여 리턴한다.")
//    @PostMapping(value = "more/validation/email-password")
//    public RspEmailPasswordCheckDto ValidateAccount(@RequestBody ReqEmailPasswordCheckDto emailPasswordCheckDto) throws JsonProcessingException {
//        String email = emailPasswordCheckDto.getEmail();
//        String password = emailPasswordCheckDto.getPassword();
//        HashMap checkResultMap = accountService.EmailPasswordCheck(email, password);
//        return commonResponseService.getRspEmailPasswordCheckDto(checkResultMap);
//    }

    @ApiOperation(value = "비밀번호 새로 설정",
            notes = "로그인과 같은 방식으로 이메일 비밀번호 체크")
    @PostMapping(value = "more/validation/newpassword")
    public HashMap<String, Object> UpdatePassword(@RequestBody ResetDto resetDto) throws JsonProcessingException {

        HashMap<String, Object> resultMap = new HashMap();

        String email = resetDto.getEmail();
        String password = resetDto.getPassword();

        try {
            System.out.println(password + "1");
            Boolean result = resetPasswordService.ResetPassword(email, password);
            resultMap.put("updated", result);
            resultMap.put("error", null);
        } catch (Exception ex) {
//            resultMap.put("verificationNum", randNum);
            resultMap.put("updated", false);
            resultMap.put("error", ex.toString());
            System.out.println(ex);
        }
        return resultMap;
    }

    //
    @ApiOperation(value = "키오스크 고유 번호 확인")
    @PostMapping(value = "more/checkuid")
    //중복검사 하는 것으로 만들어야 한다.
    public HashMap<String, Object> CheckUid(@RequestBody ResetDto resetDto) throws JsonProcessingException {
        HashMap<String, Object> resultMap = new HashMap();

        String email = resetDto.getEmail();
        NumberGenService numberGenService = new NumberGenService();
        String randNum = numberGenService.ComplicatedDigits(12, 1);

        try {
            Boolean result = resetUidService.ResetUid(email, randNum);
            resultMap.put("updated", result);
            resultMap.put("uid", randNum);
            resultMap.put("error", null);
        } catch (Exception ex) {
//            resultMap.put("verificationNum", randNum);
            resultMap.put("updated", false);
            resultMap.put("uid", randNum);
            resultMap.put("error", ex.toString());
            System.out.println(ex);
        }
        return resultMap;
    }

    @ApiOperation(value = "키오스크 고유 번호 확인",
            notes = "이메일과 질문답을 이용한다")
    @PostMapping(value = "more/newuid")
    //중복검사 하는 것으로 만들어야 한다.
    public HashMap<String, Object> NewUid(@RequestBody ResetDto resetDto) throws JsonProcessingException {
        HashMap<String, Object> resultMap = new HashMap();

        String email = resetDto.getEmail();
        NumberGenService numberGenService = new NumberGenService();
        String randNum = numberGenService.ComplicatedDigits(12, 1);

        try {
            Boolean result = resetUidService.ResetUid(email, randNum);
            resultMap.put("updated", result);
            resultMap.put("uid", randNum);
            resultMap.put("error", null);
        } catch (Exception ex) {
//            resultMap.put("verificationNum", randNum);
            resultMap.put("updated", false);
            resultMap.put("uid", randNum);
            resultMap.put("error", ex.toString());
            System.out.println(ex);
        }
        return resultMap;
    }

    @PostMapping(value = "more/newshapecode")
    public HashMap<String, Object> UpdateShapeCode(@RequestBody ResetDto resetDto) throws JsonProcessingException {
        HashMap<String, Object> resultMap = new HashMap();

        String email = resetDto.getEmail();
        String shapeCode = resetDto.getShapeCode();

        try {
            Boolean result = resetShapeCodeService.ResetShapeCode(email, shapeCode);
            resultMap.put("updated", result);
            resultMap.put("error", null);
        } catch (Exception ex) {
//            resultMap.put("verificationNum", randNum);
            resultMap.put("updated", false);
            resultMap.put("error", ex.toString());
            System.out.println(ex);
        }
        return resultMap;
    }

    @PostMapping(value = "more/validation/newphone")
    public HashMap<String, Object> UpdateValidatePhone(@RequestBody ResetDto resetDto) throws JsonProcessingException {
        HashMap<String, Object> resultMap = new HashMap();
        HashMap<String, Object> tmpMap = new HashMap();
        NumberGenService numberGenService = new NumberGenService();
        String[] numbers = {"99999999999"};
        String randNum =  numberGenService.Digits(4, 1);
        String phone = resetDto.getPhone();
        ObjectMapper mapper = new ObjectMapper();
        SendMsgService sendMsgService_new = new SendMsgService();

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

//            sendMsgService_new.NewSend("https://api-sens.ncloud.com/v1/sms/services/ncp:sms:kr:258080742855:testpowerlog/messages", json);

            resultMap.put("verificationNum", randNum);
            resultMap.put("phonePresent", false);
            resultMap.put("error", "Normal when NullPointerException:" + ex.toString());
            System.out.println(ex);
        }
        return null;
    }

    @PostMapping(value = "more/newphone")
    public HashMap<String, Object> UpdatePhone(@RequestBody ResetDto resetDto) throws JsonProcessingException {
        HashMap<String, Object> resultMap = new HashMap();
        String email = resetDto.getEmail();
        String phone = resetDto.getPhone();
        try {
            Boolean result = updatePhoneService.UpdatePhone(email, phone);
            resultMap.put("updated", result);
            resultMap.put("error", null);
        } catch (Exception ex) {
//            resultMap.put("verificationNum", randNum);
            resultMap.put("updated", false);
            resultMap.put("error", ex.toString());
            System.out.println(ex);
        }
        return null;
    }

    @ApiOperation(value = "회원 탈퇴", notes = "회원 정보를 UserTb에서 찾아 삭제한다")
    @PostMapping(value = "more/delete/user")
    public RspDeleteUserDto<Object> DeleteUser(@RequestBody ReqDeleteUserDto reqDeleteUserDto) throws JsonProcessingException {

//        String email = reqDeleteUserDto.getEmail();
//        String password = reqDeleteUserDto.getPassword();
//
//        HashMap<String, Object> checkResultMap = accountService.EmailPasswordCheck(email, password);
//        if (checkResultMap.get("error") == null) {
//            HashMap<String, Object> deleteResultMap = deleteAccountService.DeleteAccount(checkResultMap, email);
//            return commonResponseService.getRspDeleteUserDto(deleteResultMap);
//        } else {
//            return commmon.getRspDeleteUserDto(checkResultMap);
        return null;
//        }
    }
}


