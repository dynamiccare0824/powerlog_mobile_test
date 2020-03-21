package test.powerlog.mobile.springboot.web.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import test.powerlog.mobile.springboot.domain.old.NewPlannerVwRepository;
import test.powerlog.mobile.springboot.domain.view.*;
import test.powerlog.mobile.springboot.service.common.CommonResponseService;
import test.powerlog.mobile.springboot.service.common.ParamValidCheckService;
import test.powerlog.mobile.springboot.service.mobile.account.DeleteAccountService;
import test.powerlog.mobile.springboot.service.mobile.account.EmailPhoneCheckService;
import test.powerlog.mobile.springboot.service.mobile.account.EmailQuestionCheckService;
import test.powerlog.mobile.springboot.service.mobile.account.LoginService;
import test.powerlog.mobile.springboot.service.mobile.account.ResetPasswordService;
import test.powerlog.mobile.springboot.service.mobile.account.ResetShapeCodeService;
import test.powerlog.mobile.springboot.service.mobile.account.ResetUidService;
import test.powerlog.mobile.springboot.service.mobile.account.SignUpService;
import test.powerlog.mobile.springboot.service.mobile.account.UpdatePhoneService;
import test.powerlog.mobile.springboot.service.mobile.planner.PlannerService;
import test.powerlog.mobile.springboot.web.dto.mobile.request.ReqTestHistoryDto;
import test.powerlog.mobile.springboot.web.dto.mobile.request.planner.*;
import test.powerlog.mobile.springboot.web.dto.mobile.response.planner.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Api(tags = {"2. Planner/History Controller"})
@RestController
@RequestMapping("/plannerhistory")
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
    UpdatePhoneService updatePhoneService;

    @Autowired
    DeleteAccountService deleteAccountService;

    @Autowired
    LogTotalMsrVwRepository logTotalMsrVwRepository;

    @Autowired
    CommonResponseService commonResponseService;

    @Autowired
    ParamValidCheckService paramValidCheckService;

    @Autowired
    PlannerService plannerService;

    @Autowired
    NewPlannerVwRepository newPlannerVwRepository;

    @ApiOperation(value = "Year와 Month를 받아 해당하는 년월의 일자 별 일정을 돌려준다 [플래너]")
    @PostMapping(value = "/planner/main")
    public RspPlannerMainDto PlannerMain(@RequestBody @Valid ReqPlannerMainDto reqPlannerMainDto, BindingResult bindingResult) throws JsonProcessingException {
        HashMap<String, Object> resultMap = commonResponseService.getCommonHashMap();
        List<ObjectError> invalidParamList = paramValidCheckService.getInvalidParamList(bindingResult);
        if (invalidParamList == null) {
            resultMap = plannerService.getPlannerMain(reqPlannerMainDto, resultMap);
            return commonResponseService.getRspPlannerMain(invalidParamList, resultMap, reqPlannerMainDto);
        }
        return commonResponseService.getRspPlannerMain(invalidParamList, resultMap, reqPlannerMainDto);
    }


    //ing 예외처리가 되어있지 않다.
    @ApiOperation(value = "측정 기록을 확인한다 [히스토리]")
    @PostMapping(value = "/history")
    public HashMap<String, Object> testHistory(@RequestBody ReqTestHistoryDto reqTestHistoryDto) throws JsonProcessingException {
        HashMap<String, Object> commonMap = commonResponseService.getCommonHashMap();
        HashMap<String, Object> resultMap = new HashMap();
        HashMap<String, Object> resultMap2 = new HashMap();
        //
        String email = reqTestHistoryDto.getEmail();
        ArrayList<String> codeList1 = new ArrayList<String>(
                Arrays.asList("A01", "B01", "C01", "D01", "F01", "G01", "H01"));

        ArrayList<String> codeList2 = new ArrayList<String>(
                Arrays.asList("A03", "B03", "C03", "D03", "E03", "F03", "G03", "H03"));


        try {
            for (int i = 0; i < codeList1.size(); i++) {
                List<LogTotalMsrVw> tmpList = logTotalMsrVwRepository.findAllByLgTotalMsrVwEmailAndLgTotalMsrVwCommonCodeOrderByLgTotalMsrVwDateTimeDesc(email,
                        codeList1.get(i));
                if (!tmpList.isEmpty()) {
                    resultMap.put(codeList1.get(i), tmpList);
                } else {
                    resultMap.put(codeList1.get(i), null);
                }

            }
            for (int i = 0; i < codeList2.size(); i++) {
                List<LogTotalMsrVw> tmpList = logTotalMsrVwRepository.findAllByLgTotalMsrVwEmailAndLgTotalMsrVwCommonCodeOrderByLgTotalMsrVwDateTimeDesc(email,
                        codeList2.get(i));
                if (!tmpList.isEmpty()) {
                    resultMap.put(codeList2.get(i), tmpList);
                } else {
                    resultMap.put(codeList2.get(i), null);
                }
            }

//            resultMap.put("result", resultMap);
//            resultMap.put("error", null);
        }
        // 아이디가 아예 존재하지 않는 경우
        catch (Exception ex) {
//            resultMap.put("result", null);
//            resultMap.put("error", ex.toString());
        }

        resultMap2.put("isotonic", codeList1);
        resultMap2.put("isometric", codeList2);
        resultMap2.put("resultData", resultMap);
        resultMap2.put("isError", false); // 이거 고쳐야 한다
        resultMap2.put("message", "valid parameter requested");
        resultMap2.put("invlaidParamList", null);
        return resultMap2;
    }

    //ing
    @ApiOperation(value = "플랜 일정을 DB에 등록하고, 사용자에게 플랜 디테일을 보여준다 [플래너]")
    @PostMapping(value = "/planner/program/save")
    public HashMap<String, Object> ProgramSave(@RequestBody @Valid ReqProgramGenerateDto reqProgramGenerateDto, BindingResult bindingResult) throws ParseException {
        List<ObjectError> invalidParamList = paramValidCheckService.getInvalidParamList(bindingResult);
        if (invalidParamList == null) {
            return plannerService.getProgramDetail(reqProgramGenerateDto);
        }
        return null;
    }

    //ing
    @ApiOperation(value = "플랜 일정 추가시, 이미 있는 플랜이 있는지 검사 [플래너]")
    @PostMapping(value = "/planner/program/check")
    public RspProgramCheckDto ProgramCheck(@RequestBody @Valid ReqCheckProgramDto reqCheckProgramDto, BindingResult bindingResult) throws ParseException {
        HashMap<String, Object> resultMap = commonResponseService.getCommonHashMap();
        List<ObjectError> invalidParamList = paramValidCheckService.getInvalidParamList(bindingResult);
        if (invalidParamList==null) {
            resultMap = plannerService.getProgramCheck(reqCheckProgramDto, resultMap);
            return commonResponseService.getRspProgramCheckDto(invalidParamList, resultMap, reqCheckProgramDto);
        }
        return commonResponseService.getRspProgramCheckDto(invalidParamList, resultMap, reqCheckProgramDto);
    }

    //수정해야 한다
    @ApiOperation(value = "프로그램 전체 삭제 [플래너]")
    @PostMapping(value = "/planner/program/delete")
    public RspDeleteProgramDto deleteProgram(@RequestBody @Valid ReqDeleteProgramDto reqDeleteProgramDto, BindingResult bindingResult) throws ParseException {
        HashMap<String, Object> resultMap = commonResponseService.getCommonHashMap();
        List<ObjectError> invalidParamList = paramValidCheckService.getInvalidParamList(bindingResult);
        if (invalidParamList==null) {
            resultMap = plannerService.DeleteProgramByEmail(reqDeleteProgramDto, resultMap);
            return commonResponseService.getRspDeleteProgramDto(invalidParamList, resultMap, reqDeleteProgramDto);
        }
        return commonResponseService.getRspDeleteProgramDto(invalidParamList, resultMap, reqDeleteProgramDto);
    }

    @ApiOperation(value = "개인 일정 추가 [플래너]")
    @PostMapping(value = "/planner/byday/save")
    public RspByDaySaveDto ByDaySave(@RequestBody @Valid ReqByDaySaveDto reqByDaySaveDto, BindingResult bindingResult) throws ParseException {
        HashMap<String, Object> resultMap = commonResponseService.getCommonHashMap();
        List<ObjectError> invalidParamList = paramValidCheckService.getInvalidParamList(bindingResult);
        if (invalidParamList==null) {
            resultMap = plannerService.SaveByDay(reqByDaySaveDto, resultMap);
            return commonResponseService.getRspByDaySaveDto(invalidParamList, resultMap, reqByDaySaveDto);
        }
        return commonResponseService.getRspByDaySaveDto(invalidParamList, resultMap, reqByDaySaveDto);
    }

    //수정해야 한다
    @ApiOperation(value = "일정 개별 삭제 [플래너]")
    @PostMapping(value = "/planner/schedule/delete")
    public RspDeleteScheduleDto deleteSchedule(@RequestBody @Valid ReqDeleteScheduleDto reqDeleteScheduleDto, BindingResult bindingResult) throws ParseException {
        HashMap<String, Object> resultMap = commonResponseService.getCommonHashMap();
        List<ObjectError> invalidParamList = paramValidCheckService.getInvalidParamList(bindingResult);
        if (invalidParamList==null) {
            resultMap = plannerService.DeleteByIndex(reqDeleteScheduleDto, resultMap);
            return commonResponseService.getRspDeleteScheduleDto(invalidParamList, resultMap, reqDeleteScheduleDto);
        }
        return commonResponseService.getRspDeleteScheduleDto(invalidParamList, resultMap, reqDeleteScheduleDto);
    }


}
