package test.powerlog.mobile.springboot.service.kiosk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.powerlog.mobile.springboot.domain.old.NewPlannerVw;
import test.powerlog.mobile.springboot.domain.old.NewPlannerVwRepository;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OnDateWrkotService {
    @Autowired
    private NewPlannerVwRepository newPlannerVwRepository;

    public HashMap<String, Object> GetOnDateWrkot(String email) {
        System.out.println(email);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<NewPlannerVw> list = newPlannerVwRepository.findSomeCaseQueryNative(email);
        ArrayList<NewPlannerVw> programList = new ArrayList<>();
        ArrayList<NewPlannerVw> privateList = new ArrayList<>();
        HashMap<String, Object> resultMap = new HashMap<>();

        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getPlnVwOnDate().equals(sdf.format(date))){
                if(list.get(i).getPlnVwIsProgram().equals("true")){
                    programList.add(list.get(i));
                }
                else{
                    privateList.add(list.get(i));
                }
            }
        }
        if(!programList.isEmpty()){
            resultMap.put("programList", programList);
        }
        else{
            resultMap.put("programList", null);
        }
        if(!privateList.isEmpty()){
            resultMap.put("privateList", privateList);
        }
        else{
            resultMap.put("privateList", null);
        }
        return resultMap;
    }
}
