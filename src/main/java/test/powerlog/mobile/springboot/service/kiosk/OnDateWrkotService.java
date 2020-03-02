package test.powerlog.mobile.springboot.service.kiosk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.powerlog.mobile.springboot.domain.view.PlannerVw;
import test.powerlog.mobile.springboot.domain.view.PlannerVwRepository;
import test.powerlog.mobile.springboot.domain.view.UserAccountVw;
import test.powerlog.mobile.springboot.domain.view.UserAccountVwRepository;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OnDateWrkotService {
    @Autowired
    private PlannerVwRepository plannerVwRepository;

    public HashMap<String, Object> GetOnDateWrkot(String email) {
        System.out.println(email);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<PlannerVw> list = plannerVwRepository.findSomeCaseQueryNative(email);
        ArrayList<PlannerVw> programList = new ArrayList<>();
        ArrayList<PlannerVw> privateList = new ArrayList<>();
        HashMap<String, Object> resultMap = new HashMap<>();

        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getPlnVwOnDate().equals(sdf.format(date))){
                if(list.get(i).getPlnVwProgram().equals("true")){
                    programList.add(list.get(i));
                }
                else{
                    privateList.add(list.get(i));
                }
            }
        }
        resultMap.put("programList", programList);
        resultMap.put("privateList", privateList);
        return resultMap;
    }
}
