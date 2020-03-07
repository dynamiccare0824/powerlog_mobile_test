package test.powerlog.mobile.springboot.service.mobile.planner;

import com.sun.jmx.remote.internal.ArrayQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.powerlog.mobile.springboot.domain.table.UserTb;
import test.powerlog.mobile.springboot.domain.table.UserTbRepository;
import test.powerlog.mobile.springboot.domain.view.LogLateMsrVw;
import test.powerlog.mobile.springboot.domain.view.LogLateMsrVwRepository;
import test.powerlog.mobile.springboot.domain.view.UserAccountVw;
import test.powerlog.mobile.springboot.domain.view.UserAccountVwRepository;
import test.powerlog.mobile.springboot.service.mobile.NumberGenService;
import test.powerlog.mobile.springboot.service.mobile.SendEmailService;
import test.powerlog.mobile.springboot.service.mobile.old.SignUpService;
import test.powerlog.mobile.springboot.web.dto.mobile.request.account.ReqRegisterDto;
import test.powerlog.mobile.springboot.web.dto.mobile.request.account.SignUpDto;
import test.powerlog.mobile.springboot.web.dto.mobile.request.planner.ReqProgramDetailDto;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class PlannerService {

    @Autowired
    private UserTbRepository userTbRepository;
    @Autowired
    private UserAccountVwRepository userAccountVwRepository;
    @Autowired
    private SendEmailService sendEmailService;
    @Autowired
    private NumberGenService numberGenService;
    @Autowired
    private SignUpService signUpService;
    @Autowired
    private LogLateMsrVwRepository logLateMsrVwRepository;


    public HashMap<String, Object> saveProgramRequest(ReqProgramDetailDto reqProgramDetailDto) {
        HashMap<String, Object> tmpMap = new HashMap();
        return tmpMap;
    }

//    public HashMap<String, Object> setProgramRequest(ReqProgramDetailDto reqProgramDetailDto) {
//        ArrayList<Object> twoPerWeekList = new ArrayList<Object>(ArrayQueue)
//        twoPerWeekList
//
//
//        HashMap<String, Object> tmpMap = new HashMap();
//
//
//        return tmpMap;
//    }
}
