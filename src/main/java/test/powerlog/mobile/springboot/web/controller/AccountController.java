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
import test.powerlog.mobile.springboot.service.common.ParamValidCheckService;
import test.powerlog.mobile.springboot.service.mobile.*;
import test.powerlog.mobile.springboot.service.mobile.old.*;
import test.powerlog.mobile.springboot.web.dto.common.CommonResponseDto;
import test.powerlog.mobile.springboot.web.dto.mobile.request.account.*;
import test.powerlog.mobile.springboot.web.dto.mobile.response.*;

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

    @ApiOperation(value = "회원가입",
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
            accountService.Signup(signUpDto); // save 실행
            return commonResponseService.getRspRegisterDto(null, reqRegisterDto.getEmail());
        }
    }
//
//    @ApiOperation(value = "핸드폰 번호로 비밀번호 찾기",
//            notes = "이메일과 핸드폰 번호를 이용한다")
//    @PostMapping(value = "lost/validation/phone")
//    public RspEmailPasswordCheckDto<Object> ValidatePhoneSendMsg(@RequestBody @Valid ReqLostValidPhoneDto reqLostValidPhoneDto, BindingResult bindingResult) throws JsonProcessingException {
//        HashMap<String, Object> tmpMap = new HashMap<>();
//
//        if (bindingResult.hasErrors()) {
//            StringBuffer errorString = new StringBuffer();
//            List<ObjectError> invalidParamList = bindingResult.getAllErrors();
//            return commonResponseService.getRspEmailPasswordCheckDto(invalidParamList, null);
//        } else {
//
//            HashMap<String, Object> resultMap = new HashMap();
//            String email = reqLostValidPhoneDto.getEmail();
//            String phone = reqLostValidPhoneDto.getPhone();
//            //이 이메일과 핸드폰 번호가 맞는 번호인지 체크한다
//            if (checkService.emailPhoneCheck(email, phone)) {
//                //맞다면 핸드폰으로 인증번호 전송
//                NumberGenService numberGenService = new NumberGenService();
//                String randNum = numberGenService.Digits(4, 1);
//                try {
//                    String sendMsgResult = sendMsgService.buildJsonSendMsg(phone, randNum, tmpMap);
//                    tmpMap.put("verificationNum", randNum);
//                    tmpMap.put("sendMsgResult", sendMsgResult);
//                }
//                //핸드폰 번호가 DB에 없는 것은 맞는데 서버 오류로 문자가 보내지지 않는 문제가 발생했다면
//                catch (Exception ex) {
//                    tmpMap.replace("error", ex.toString());
////                    return commonResponseService.getRspDupCheckSendMsgDto(null, tmpMap);
//                }
//            } else {
//                return null;
//            }
//            return null;
//        }
//    }




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

}


