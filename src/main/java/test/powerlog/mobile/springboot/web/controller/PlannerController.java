package test.powerlog.mobile.springboot.web.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import test.powerlog.mobile.springboot.domain.view.*;
import test.powerlog.mobile.springboot.service.mobile.*;
import test.powerlog.mobile.springboot.web.dto.mobile.request.ReqTestHistoryDto;
import test.powerlog.mobile.springboot.web.dto.mobile.request.UserAccountDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Api(tags = {"3. Planner/History Controller"})
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

    @PostMapping(value = "/historytest")
    public HashMap<String, Object> testHistory(@RequestBody ReqTestHistoryDto reqTestHistoryDto) throws JsonProcessingException {
        HashMap<String, Object> resultMap = new HashMap();
        HashMap<String, Object> resultMap2 = new HashMap();
        String email = reqTestHistoryDto.getEmail();
        ArrayList<String> codeList1 =  new ArrayList<>();
        codeList1.add("A01");
        codeList1.add("B01");
        codeList1.add("C01");
        codeList1.add("D01");
        codeList1.add("E01");
        codeList1.add("F01");
        codeList1.add("G01");
        codeList1.add("H01");

        ArrayList<String> codeList2 =  new ArrayList<>();
        codeList2.add("A03");
        codeList2.add("B03");
        codeList2.add("C03");
        codeList2.add("D03");
        codeList2.add("E03");
        codeList2.add("F03");
        codeList2.add("G03");
        codeList2.add("H03");


        try{
            for (int i = 0; i < codeList1.size(); i++) {
                List<LogTotalMsrVw> tmpList = logTotalMsrVwRepository.findAllByLgTotalMsrVwEmailAndLgTotalMsrVwCommonCodeOrderByLgTotalMsrVwLogDateDesc(email,
                        codeList1.get(i));
                if(!tmpList.isEmpty()){
                    resultMap.put(codeList1.get(i), tmpList);
                }
                else{
                    resultMap.put(codeList1.get(i), null);
                }

            }
            for (int i = 0; i < codeList2.size(); i++) {
                List<LogTotalMsrVw> tmpList = logTotalMsrVwRepository.findAllByLgTotalMsrVwEmailAndLgTotalMsrVwCommonCodeOrderByLgTotalMsrVwLogDateDesc(email,
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

}
