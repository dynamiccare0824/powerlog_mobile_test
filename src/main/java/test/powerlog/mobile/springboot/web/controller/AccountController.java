package test.powerlog.mobile.springboot.web.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import test.powerlog.mobile.springboot.domain.view.*;
import test.powerlog.mobile.springboot.service.common.CommonResponseService;
import test.powerlog.mobile.springboot.service.common.ParamValidCheckService;
import test.powerlog.mobile.springboot.service.mobile.account.*;
import test.powerlog.mobile.springboot.service.mobile.account.DeleteAccountService;
import test.powerlog.mobile.springboot.service.mobile.account.DupCheckEmailService;
import test.powerlog.mobile.springboot.service.mobile.account.DupCheckPhoneService;
import test.powerlog.mobile.springboot.service.mobile.account.EmailPhoneCheckService;
import test.powerlog.mobile.springboot.service.mobile.account.EmailQuestionCheckService;
import test.powerlog.mobile.springboot.service.mobile.account.LoginService;
import test.powerlog.mobile.springboot.service.mobile.account.ResetPasswordService;
import test.powerlog.mobile.springboot.service.mobile.account.ResetShapeCodeService;
import test.powerlog.mobile.springboot.service.mobile.account.ResetUidService;
import test.powerlog.mobile.springboot.service.mobile.account.SignUpService;
import test.powerlog.mobile.springboot.service.mobile.account.UpdatePhoneService;
import test.powerlog.mobile.springboot.web.dto.common.CommonResponseDto;
import test.powerlog.mobile.springboot.web.dto.mobile.request.account.*;
import test.powerlog.mobile.springboot.web.dto.mobile.response.account.*;

import javax.validation.*;
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

    @Autowired
    private ParamValidCheckService paramValidCheckService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //Completed
    //회원정보 틀렸을 때 오류
    @ApiOperation(value = "회원 로그인 [로그인]", notes = "이메일 아이디와 비밀번호를 받아 로그인한다!")
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
            resultMap.put("planExpired", accountService.ifExpired(email));
            return commonResponseService.getRspLoginDto(null, logRecordMap, logRecordFormMap, resultMap);
        }
    }

    //Completed
    @ApiOperation(value = "이메일 중복 검사 [회원가입]", notes = "회원가입 시, 이메일 아이디가 DB에 이미 존재하는지 여부를 검사한다.")
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
    @ApiOperation(value = "핸드폰 중복 검사 + 조건 충족 시 인증번호 발송 [회원가입]",
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


    @ApiOperation(value = "신체 정보 유효성 검사 [회원가입]", notes = "")
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

    @ApiOperation(value = "회원 정보 등록 [회원가입] ",
            notes = "회원가입")
    @PostMapping(value = "signup/register")
    public RspRegisterDto Register(@RequestBody @Valid ReqRegisterDto reqRegisterDto, BindingResult bindingResult) throws JsonProcessingException {
        HashMap<String, Object> tmpMap = new HashMap();
        List<ObjectError> invalidParamList = paramValidCheckService.getInvalidParamList(bindingResult);

        if (invalidParamList != null) {
            return commonResponseService.getRspRegisterDto(invalidParamList, null);
        } else {
            HashMap<String, Object> resultMap = new HashMap();
            SignUpDto signUpDto = accountService.ToRegisterForm(reqRegisterDto);
            signUpDto.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
            accountService.Signup(signUpDto); // save 실행
            return commonResponseService.getRspRegisterDto(null, reqRegisterDto.getEmail());
        }
    }

    //ing
    @ApiOperation(value = "핸드폰 번호로 비밀번호 찾기 [비밀번호 찾기]",
            notes = "이메일과 핸드폰 번호를 이용한다")
    @PostMapping(value = "lost/validation/phone")
    public RspLostValidPhoneDto ValidatePhoneSendMsg(@RequestBody @Valid ReqLostValidPhoneDto reqLostValidPhoneDto, BindingResult bindingResult) throws JsonProcessingException {
        HashMap<String, Object> commonMap = commonResponseService.getCommonHashMap();

        if (bindingResult.hasErrors()) {
            StringBuffer errorString = new StringBuffer();
            List<ObjectError> invalidParamList = bindingResult.getAllErrors();
            return commonResponseService.getRspLostValidPhoneDto(invalidParamList, null);
        } else {
            HashMap<String, Object> resultMap = new HashMap();
            String email = reqLostValidPhoneDto.getEmail();
            String phone = reqLostValidPhoneDto.getPhone();
            //이 이메일과 핸드폰 번호가 맞는 번호인지 체크한다
            if (checkService.emailPhoneCheck(email, phone)) {
                //맞다면 핸드폰으로 인증번호 전송
                commonMap.replace("isMatch", true);
                NumberGenService numberGenService = new NumberGenService();
                String randNum = numberGenService.Digits(4, 1);
                try {
                    String sendMsgResult = sendMsgService.buildJsonSendMsg(phone, randNum, commonMap);
                    commonMap.replace("verificationNum", randNum);
                }
                //핸드폰 번호가 DB에 없는 것은 맞는데 서버 오류로 문자가 보내지지 않는 문제가 발생했다면
                catch (Exception ex) {
                    commonMap.replace("error", ex.toString());
                    return commonResponseService.getRspLostValidPhoneDto(null, commonMap);
                }
            } else {
                commonMap.replace("isMatch", false);
                return commonResponseService.getRspLostValidPhoneDto(null, commonMap);
            }
        }
        return commonResponseService.getRspLostValidPhoneDto(null, commonMap);
    }

    //ing
    @ApiOperation(value = "등록된 이메일로 임시 비밀번호 발송 [비밀번호 찾기]",
            notes = "이메일이 존재하는지 검사하고, 있다면 임시 비밀번호를 발송한다")
    @PostMapping(value = "lost/validation/email-question")
    public HashMap<String, Object> ValidateQuestionSendMail(@RequestBody ReqEmailQuestionDto emailQuestionDto) throws JsonProcessingException {

        HashMap<String, Object> resultMap = new HashMap();

        String email = emailQuestionDto.getEmail();
        String questionCode = emailQuestionDto.getQuestionCode();
        String questionAnswer = emailQuestionDto.getQuestionAnswer();
        String randNum = numberGenService.Digits(12, 1);

        try {
            Boolean result = emailQuestionCheckService.emailQuestionCheck(email, questionCode, questionAnswer, randNum);
            resultMap.put("emailPresent", true);
            resultMap.put("isMatch", result);
            resultMap.put("error", null);
        } catch (Exception ex) {
            resultMap.put("emailPresent", false);
            resultMap.put("isMatch", false);
            resultMap.put("error", ex.toString());
            System.out.println(ex);
        }
        return resultMap;
    }

    @ApiOperation(value = "계정 본인 확인 [더보기]",
            notes = "더보기 메뉴에서 계정 본인 확인을 위해서 비밀번호 재검사, 이메일과 패스워드 isMatch 체크하여 리턴한다.")
    @PostMapping(value = "more/password/validation/email-password")
    public RspEmailPasswordCheckDto ValidateAccount(@RequestBody @Valid ReqEmailPasswordCheckDto emailPasswordCheckDto, BindingResult bindingResult) throws JsonProcessingException {
        HashMap<String, Object> tmpMap = new HashMap();
        List<ObjectError> invalidParamList = paramValidCheckService.getInvalidParamList(bindingResult);

        if (invalidParamList != null) {
            return commonResponseService.getRspEmailPasswordCheckDto(invalidParamList, null);
        } else {
            String email = emailPasswordCheckDto.getEmail();
            String password = emailPasswordCheckDto.getPassword();
            HashMap checkResultMap = accountService.EmailPasswordCheck(email, password);
            return commonResponseService.getRspEmailPasswordCheckDto(null, checkResultMap);
        }
    }

    @ApiOperation(value = "비밀번호 변경 [더보기]",
            notes = "더보기 메뉴 혹은 비밀번호 찾기에서 계정 확인이 끝난 후 비밀번호 재설정")
    @PostMapping(value = "more/password/update")
    public HashMap<String, Object> UpdatePassword(@RequestBody ReqUpdatePasswordDto reqUpdatePasswordDto) throws JsonProcessingException {

        HashMap<String, Object> resultMap = new HashMap();

        String email = reqUpdatePasswordDto.getEmail();
        String password = passwordEncoder.encode(reqUpdatePasswordDto.getPassword());

        try {
            Boolean result = resetPasswordService.ResetPassword(email, password);
            resultMap.put("isUpdated", result);
            resultMap.put("isError", false);
            resultMap.put("error", null);
        } catch (Exception ex) {
//            resultMap.put("verificationNum", randNum);
            resultMap.put("isUpdated", false);
            resultMap.put("isError", true);
            resultMap.put("error", ex.toString());
            System.out.println(ex);
        }
        return resultMap;
    }
//

    @PostMapping(value = "/more/uid/get")
    @ApiOperation(value = "고유번호 확인 [더보기]")
    public HashMap<String, Object> GetUid(@RequestBody ReqUidDto reqUidDto) throws JsonProcessingException {
        HashMap<String, Object> resultMap = new HashMap();

        String email = reqUidDto.getEmail();
        try {
            userAccountVWRepository.findById(email).get().getLoginVwUid();
            resultMap.put("uid", userAccountVWRepository.findById(email).get().getLoginVwUid());
            resultMap.put("isError", false);
            resultMap.put("error", null);
        } catch (Exception ex) {
            resultMap.put("uid", null);
            resultMap.put("isError", true);
            resultMap.put("error", ex.toString());
            System.out.println(ex);
        }
        return resultMap;
    }

    @PostMapping(value = "/more/uid/update")
    @ApiOperation(value = "고유번호 새로고침 [더보기]")
    //중복검사 하는 것으로 만들어야 한다.
    public HashMap<String, Object> UpdateUid(@RequestBody ReqUidDto reqUidDto) throws JsonProcessingException {
        HashMap<String, Object> resultMap = new HashMap();

        String email = reqUidDto.getEmail();
        NumberGenService numberGenService = new NumberGenService();
        String randNum = numberGenService.ComplicatedDigits(12, 1);
        Boolean result = resetUidService.ResetUid(email, randNum);
        if (result) {
            resultMap.put("isUpdated", result);
            resultMap.put("uid", randNum);
            resultMap.put("isError", false);
        } else {
            resultMap.put("isUpdated", false);
            resultMap.put("uid", null);
            resultMap.put("isError", true);
        }
        return resultMap;
    }

    //
    @PostMapping(value = "more/shapecode/update")
    @ApiOperation(value = "운동 목적 변경 [더보기]")
    public HashMap<String, Object> UpdateShapeCode(@RequestBody ReqUpdateShapeCodeDto reqUpdateShapeCodeDto) throws JsonProcessingException {
        HashMap<String, Object> resultMap = new HashMap();

        String email = reqUpdateShapeCodeDto.getEmail();
        String shapeCode = reqUpdateShapeCodeDto.getShapeCode();


        Boolean result = resetShapeCodeService.ResetShapeCode(email, shapeCode);
        if (result) {
            resultMap.put("isUpdated", result);
            resultMap.put("isError", false);
        } else {
            resultMap.put("isUpdated", false);
            resultMap.put("isError", true);
        }
        return resultMap;
    }

    @PostMapping(value = "/more/phone/validation")
    @ApiOperation(value = "핸드폰 번호 변경을 위한 사용기기 인증 [더보기]")
    public RspDupCheckSendMsg UpdateValidatePhone(@RequestBody @Valid ReqValidPhoneDto reqValidPhoneDto, BindingResult bindingResult) throws JsonProcessingException {
        HashMap<String, Object> tmpMap = new HashMap<>();

        if (bindingResult.hasErrors()) {
            StringBuffer errorString = new StringBuffer();
            List<ObjectError> invalidParamList = bindingResult.getAllErrors();
            return commonResponseService.getRspDupCheckSendMsgDto(invalidParamList, tmpMap);
        } else {
            String phone = reqValidPhoneDto.getPhone();
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

    //
    @ApiOperation(value = "새로운 핸드폰 번호 등록 [더보기]")
    @PostMapping(value = "/more/phone/update")
    public HashMap<String, Object> UpdatePhone(@RequestBody ReqUpdatePhoneDto reqUpdatePhoneDto) throws JsonProcessingException {
        HashMap<String, Object> resultMap = new HashMap();
        String email = reqUpdatePhoneDto.getEmail();
        String phone = reqUpdatePhoneDto.getPhone();
        Boolean result = updatePhoneService.UpdatePhone(email, phone);
        if (result) {
            resultMap.put("isUpdated", result);
            resultMap.put("isError", false);
        } else {
            resultMap.put("isUpdated", result);
            resultMap.put("isError", true);
        }
        return resultMap;
    }


    @ApiOperation(value = "신체 정보 변경 [더보기]")
    @PostMapping(value = "more/bodydetail/update")
    public HashMap<String, Object> UpdateBodyDetail(@RequestBody @Valid ReqUpdateBodyDetailDto reqUpdateBodyDetailDto, BindingResult bindingResult) throws JsonProcessingException {
        HashMap<String, Object> resultMap = new HashMap<>();
        if (bindingResult.hasErrors()) {
            StringBuffer errorString = new StringBuffer();
            List<ObjectError> invalidParamList = bindingResult.getAllErrors();
            resultMap.put("error", invalidParamList);
            resultMap.put("isError", true);
            resultMap.put("isUpdated", false);
            // 파라미터 오류 정보만 있고, map도 없을 것
        } else {
            if(accountService.UpdateBodyDetail(reqUpdateBodyDetailDto)){
                resultMap.put("error", null);
                resultMap.put("isError", false);
            }
            else{
                resultMap.put("error", "unknown error or cannot find qualified data");
                resultMap.put("isError", true);
            }
            resultMap.put("isUpdated", accountService.UpdateBodyDetail(reqUpdateBodyDetailDto));
        }
        return resultMap;
    }

    @ApiOperation(value = "회원 탈퇴 [더보기]")
    @PostMapping(value = "more/user/delete")
    public HashMap<String, Object> DeleteUser(@RequestBody ReqDeleteUserDto reqDeleteUserDto) throws JsonProcessingException {

        HashMap<String, Object> resultMap= new HashMap<>();
        String email = reqDeleteUserDto.getEmail();
        String password = reqDeleteUserDto.getPassword();

        HashMap<String, Object> checkResultMap = accountService.EmailPasswordCheck(email, password);
        if ((Boolean) checkResultMap.get("isMatch")) {
            HashMap<String, Object> deleteResultMap = deleteAccountService.DeleteAccount(checkResultMap, email);
            if(deleteResultMap.get("error")==null){
                resultMap.put("isError", false);
                resultMap.put("isMatch", true);
                resultMap.put("isDone", true);
            }
            else{
                resultMap.put("isError", true);
                resultMap.put("isMatch", true);
                resultMap.put("isDone", true);
            }

            return resultMap;
        } else {
            resultMap.put("isError", true);
            resultMap.put("isMatch", false);
            resultMap.put("isDone", false);
            return resultMap;
        }
    }
}


