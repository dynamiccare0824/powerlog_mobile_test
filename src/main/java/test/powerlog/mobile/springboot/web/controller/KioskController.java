package test.powerlog.mobile.springboot.web.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import test.powerlog.mobile.springboot.domain.view.*;
import test.powerlog.mobile.springboot.service.common.CommonResponseService;
import test.powerlog.mobile.springboot.service.common.ParamValidCheckService;
import test.powerlog.mobile.springboot.service.kiosk.OnDateWrkotService;
import test.powerlog.mobile.springboot.service.kiosk.UidLoginService;
import test.powerlog.mobile.springboot.service.mobile.*;
import test.powerlog.mobile.springboot.web.dto.kiosk.request.ReqKioskLoginDto;
import test.powerlog.mobile.springboot.web.dto.kiosk.response.RspKioskLoginDto;

import javax.validation.Valid;
import java.lang.reflect.Array;
import java.util.*;

@Api(tags = {"3. Kiosk"})
@RestController
@RequestMapping("/kiosk")
public class KioskController {

    @Autowired
    private UserAccountVwRepository userAccountVWRepository;

    @Autowired
    private LogLateMsrVwRepository logLateMsrVwRepository;

    @Autowired
    private LogTotalWrkotVwRepository logTotalWrkotVwRepository;

    @Autowired
    private LoginService loginService;

    @Autowired
    private EmailPhoneCheckService emailPhoneCheckService;

    @Autowired
    private  EmailQuestionCheckService emailQuestionCheckService;

    @Autowired
    private SignUpService signUpService;

    @Autowired
    private  ResetPasswordService resetPasswordService;

    @Autowired
    private  ResetUidService resetUidService;

    @Autowired
    private   ResetShapeCodeService resetShapeCodeService;

    @Autowired
    private  UpdatePhoneService updatePhoneService;

    @Autowired
    private  DeleteAccountService deleteAccountService;

    @Autowired
    private  CommonResponseService commonResponseService;

    @Autowired
    private OnDateWrkotService onDateWrkotService;

    @Autowired
    private ParamValidCheckService paramValidCheckService;

    @Autowired
    private UidLoginService uidLoginService;

    @Autowired
    private PlannerVwRepository plannerVwRepository;

    @Autowired
    private WorkoutCodeVwRepository workoutCodeVwRepository;

    @PostMapping(value = "/uidlogin")
    public RspKioskLoginDto<PlannerVw> UidLogin(@RequestBody @Valid ReqKioskLoginDto reqKioskLoginDto, BindingResult bindingResult) throws JsonProcessingException {
        HashMap<String, Object> uidLoginResult = new HashMap();
        HashMap<String, Object> workoutCodeVwMap = new HashMap();
        String uid = reqKioskLoginDto.getUid();
        
        List<ObjectError> invalidParamList = paramValidCheckService.getInvalidParamList(bindingResult);
        List<WorkoutCodeVw> workoutCodeVwList = workoutCodeVwRepository.findAll();

        for (int i = 0; i < workoutCodeVwList.size(); i++) {
            HashMap<String, Object> tmpHashMap = new HashMap<>();
            tmpHashMap.put("wcCommonNm", workoutCodeVwList.get(i).getWcCommonNm());
            tmpHashMap.put("wcCommonMuscle", workoutCodeVwList.get(i).getWcCommonMuscle());
            tmpHashMap.put("wcCommonType", workoutCodeVwList.get(i).getWcCommonType());
            workoutCodeVwMap.put(workoutCodeVwList.get(i).getWcCommonCode(), tmpHashMap);
            }

        // 파라미터 오류가 존재한다면
        if(invalidParamList!= null){
            return commonResponseService.getRspKioskLoginDto(invalidParamList, null, null, workoutCodeVwMap);
        }

        // 파라미터 오류가 존재하지 않으면 db에 uid 존재 여부 조회
        uidLoginResult = uidLoginService.getUidLoginResult(uidLoginResult, uid);

        //if: 사용자가 DB에 존재한다면 email 가져와서 plannerVW 조회
        if((Boolean) uidLoginResult.get("isPresent")){
            String email = (String) uidLoginResult.get("email");
            HashMap<String, Object> onDateWrkotMap = onDateWrkotService.GetOnDateWrkot(email);
            return commonResponseService.getRspKioskLoginDto(null, onDateWrkotMap, uidLoginResult, workoutCodeVwMap);
        }
        //else: /사용자가 DB에 존재하지 않으면 결과 리턴
        else{
            return commonResponseService.getRspKioskLoginDto(null, null, uidLoginResult, workoutCodeVwMap);
        }
    }

//    @PostMapping(value = "/testuidlogin")
//    public RspKioskLoginDto<PlannerVw> Test(@RequestBody @Valid ReqKioskLoginDto reqKioskLoginDto, BindingResult bindingResult) throws JsonProcessingException {
//        HashMap<String, Object> uidLoginResult = new HashMap();
//        String uid = reqKioskLoginDto.getUid();
////        userAccountVWRepository.findAll
//
//        List<ObjectError> invalidParamList = paramValidCheckService.getInvalidParamList(bindingResult);
//        // 파라미터 오류가 존재한다면
//        if(invalidParamList!= null){
//            return commonResponseService.getRspKioskLoginDto(invalidParamList, null, null);
//        }
//
//        // 파라미터 오류가 존재하지 않으면 db에 uid 존재 여부 조회
//        uidLoginResult = uidLoginService.getUidLoginResult(uidLoginResult, uid);
//
//        //if: 사용자가 DB에 존재한다면 email 가져와서 plannerVW 조회
//        if((Boolean) uidLoginResult.get("isPresent")){
//            String email = (String) uidLoginResult.get("email");
//            HashMap<String, Object> onDateWrkotMap = onDateWrkotService.GetOnDateWrkot(email);
//            return commonResponseService.getRspKioskLoginDto(null, onDateWrkotMap, uidLoginResult);
//        }
//        //else: /사용자가 DB에 존재하지 않으면 결과 리턴
//        else{
//            return commonResponseService.getRspKioskLoginDto(null, null, uidLoginResult);
//        }
//    }
}
