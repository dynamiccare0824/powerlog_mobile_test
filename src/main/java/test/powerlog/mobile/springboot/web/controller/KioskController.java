package test.powerlog.mobile.springboot.web.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import test.powerlog.mobile.springboot.domain.table.LogTb;
import test.powerlog.mobile.springboot.domain.table.LogTbRepository;
import test.powerlog.mobile.springboot.domain.table.WorkoutTb;
import test.powerlog.mobile.springboot.domain.view.*;
import test.powerlog.mobile.springboot.service.common.CommonResponseService;
import test.powerlog.mobile.springboot.service.common.ParamValidCheckService;
import test.powerlog.mobile.springboot.service.kiosk.OnDateWrkotService;
import test.powerlog.mobile.springboot.service.kiosk.SaveMeasureService;
import test.powerlog.mobile.springboot.service.kiosk.SaveWorkoutService;
import test.powerlog.mobile.springboot.service.kiosk.UidLoginService;
import test.powerlog.mobile.springboot.service.mobile.account.DeleteAccountService;
import test.powerlog.mobile.springboot.service.mobile.account.EmailPhoneCheckService;
import test.powerlog.mobile.springboot.service.mobile.account.EmailQuestionCheckService;
import test.powerlog.mobile.springboot.service.mobile.account.LoginService;
import test.powerlog.mobile.springboot.service.mobile.account.ResetPasswordService;
import test.powerlog.mobile.springboot.service.mobile.account.ResetShapeCodeService;
import test.powerlog.mobile.springboot.service.mobile.account.ResetUidService;
import test.powerlog.mobile.springboot.service.mobile.account.SignUpService;
import test.powerlog.mobile.springboot.service.mobile.account.UpdatePhoneService;
import test.powerlog.mobile.springboot.web.dto.kiosk.request.ReqKioskLoginDto;
import test.powerlog.mobile.springboot.web.dto.kiosk.request.ReqKioskMeasureDto;
import test.powerlog.mobile.springboot.web.dto.kiosk.request.ReqKioskWorkoutDto;
import test.powerlog.mobile.springboot.web.dto.kiosk.response.RspKioskLoginDto;
import test.powerlog.mobile.springboot.web.dto.kiosk.response.RspKioskWorkoutDto;

import javax.validation.Valid;
import java.util.*;

@Api(tags = {"3. Kiosk"})
@RestController
@RequestMapping("/kiosk")
public class KioskController {
    @Autowired
    private  CommonResponseService commonResponseService;

    @Autowired
    private OnDateWrkotService onDateWrkotService;

    @Autowired
    private ParamValidCheckService paramValidCheckService;

    @Autowired
    private UidLoginService uidLoginService;

    @Autowired
    private LogTbRepository logTbRepository;

    @Autowired
    private WorkoutCodeVwRepository workoutCodeVwRepository;

    @Autowired
    private SaveWorkoutService saveWorkoutService;

    @Autowired
    private SaveMeasureService saveMeasureService;

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

    // ing
    // isProgram과 onSchedule 반영되지 않음
    @PostMapping(value = "/save/workout")
    public HashMap<String, Object> SaveWorkout(@RequestBody @Valid ReqKioskWorkoutDto reqKioskWorkoutDto, BindingResult bindingResult) throws JsonProcessingException {
        HashMap<String, Object> commonMap = commonResponseService.getCommonHashMap();
        List<ObjectError> invalidParamList = paramValidCheckService.getInvalidParamList(bindingResult);
        if(invalidParamList!=null){
            commonMap.replace("isError", true);
            commonMap.replace("message", invalidParamList.get(0).getDefaultMessage());
//            return commonResponseService.getRspKioskWorkoutDto(invalidParamList, commonMap);
        }
        else{
            WorkoutTb tmpWorkoutTb = saveWorkoutService.ToSaveRecordForm(reqKioskWorkoutDto);
            try{
                commonMap.replace("isError", false);
                System.out.println(commonMap.toString() + "1");
                saveWorkoutService.SaveRecord(tmpWorkoutTb);
            }
            catch(Exception ex){
                commonMap.replace("isError", true);
                commonMap.replace("message", ex.toString());
            }
        }
        System.out.println(commonMap);
        return commonMap;
    }

    @PostMapping(value = "/save/measure")
    public HashMap<String, Object>  SaveMeasure(@RequestBody @Valid ReqKioskMeasureDto reqKioskMeasureDto, BindingResult bindingResult) throws JsonProcessingException {
        HashMap<String, Object> commonMap = commonResponseService.getCommonHashMap();
        List<ObjectError> invalidParamList = paramValidCheckService.getInvalidParamList(bindingResult);
        if(invalidParamList!=null){
            commonMap.replace("isError", true);
            commonMap.replace("message", invalidParamList.get(0).getDefaultMessage());
//            return commonResponseService.getRspKioskMeasureDto(invalidParamList, commonMap);
        }
        else{
            LogTb tmpLogTb = saveMeasureService.ToSaveRecordForm(reqKioskMeasureDto);
            try{
                commonMap.replace("isError", false);
                System.out.println(commonMap.toString() + "1");
                saveMeasureService.SaveRecord(tmpLogTb);
            }
            catch(Exception ex){
                commonMap.replace("isError", true);
                commonMap.replace("message", ex.toString());
            }
        }
        System.out.println(commonMap);
        return commonMap;
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
