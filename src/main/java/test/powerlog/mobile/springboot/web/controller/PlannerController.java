package test.powerlog.mobile.springboot.web.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import test.powerlog.mobile.springboot.domain.view.*;
import test.powerlog.mobile.springboot.service.common.CommonResponseService;
import test.powerlog.mobile.springboot.service.mobile.old.*;
import test.powerlog.mobile.springboot.web.dto.common.CommonResponseDto;
import test.powerlog.mobile.springboot.web.dto.mobile.request.ReqTestHistoryDto;
import test.powerlog.mobile.springboot.web.dto.mobile.request.planner.ReqProgramDetailDto;

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

//    @PostMapping(value = "/plannerByMonth")
//    public HashMap<String, Object> PlannerByMonth(@RequestBody UserAccountDto userAccountDto) throws JsonProcessingException {
//        HashMap<String, Object> resultMap = new HashMap();
//        String email = userAccountDto.getEmail();
//
//        try{
//            resultMap.put("result", logTotalWrkotVwRepository.findAllByLgTotalWrkotVwEmail(email));
//            resultMap.put("error", null);
//        }
//        // 아이디가 아예 존재하지 않는 경우
//        catch(Exception ex){
//            resultMap.put("result", null);
//            resultMap.put("error", ex.toString());
//        }
//        return resultMap;
//    }

    //ing 예외처리가 되어있지 않다.
    @PostMapping(value = "/historytest")
    public HashMap<String, Object> testHistory(@RequestBody ReqTestHistoryDto reqTestHistoryDto) throws JsonProcessingException {
        HashMap<String, Object> resultMap = new HashMap();
        HashMap<String, Object> resultMap2 = new HashMap();
        //
        String email = reqTestHistoryDto.getEmail();
        ArrayList<String> codeList1 =  new ArrayList<String>(
                Arrays.asList("A01", "B01", "C01", "D01", "F01", "G01", "H01"));

        ArrayList<String> codeList2 =  new ArrayList<String>(
                Arrays.asList("A03", "B03", "C03", "D03", "E03", "F03", "G03", "H03"));


        try{
            for (int i = 0; i < codeList1.size(); i++) {
                List<LogTotalMsrVw> tmpList = logTotalMsrVwRepository.findAllByLgTotalMsrVwEmailAndLgTotalMsrVwCommonCodeOrderByLgTotalMsrVwDateTimeDesc(email,
                        codeList1.get(i));
                if(!tmpList.isEmpty()){
                    resultMap.put(codeList1.get(i), tmpList);
                }
                else{
                    resultMap.put(codeList1.get(i), null);
                }

            }
            for (int i = 0; i < codeList2.size(); i++) {
                List<LogTotalMsrVw> tmpList = logTotalMsrVwRepository.findAllByLgTotalMsrVwEmailAndLgTotalMsrVwCommonCodeOrderByLgTotalMsrVwDateTimeDesc(email,
                        codeList2.get(i));
                if(!tmpList.isEmpty()){
                    resultMap.put(codeList2.get(i), tmpList);
                }
                else{
                    resultMap.put(codeList2.get(i), null);
                }
            }

//            resultMap.put("result", resultMap);
//            resultMap.put("error", null);
        }
        // 아이디가 아예 존재하지 않는 경우
        catch(Exception ex){
//            resultMap.put("result", null);
//            resultMap.put("error", ex.toString());
        }

        resultMap2.put("isotonic", codeList1);
        resultMap2.put("isometric", codeList2);
        resultMap2.put("resultData", resultMap);
        return resultMap2;
    }

    @PostMapping(value = "/planner/program/detail")
    public HashMap<String, Object> ProgramDetail(@RequestBody ReqProgramDetailDto reqProgramDetailDto) throws JsonProcessingException {
        HashMap<String, Object> resultMap = commonResponseService.getCommonHashMap();
        return null;
    }
}
