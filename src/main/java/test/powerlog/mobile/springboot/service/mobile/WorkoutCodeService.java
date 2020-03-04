package test.powerlog.mobile.springboot.service.mobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.powerlog.mobile.springboot.domain.table.UserTb;
import test.powerlog.mobile.springboot.domain.table.UserTbRepository;
import test.powerlog.mobile.springboot.domain.view.WorkoutCodeVw;
import test.powerlog.mobile.springboot.domain.view.WorkoutCodeVwRepository;
import test.powerlog.mobile.springboot.web.dto.mobile.request.SignUpDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class WorkoutCodeService {
    @Autowired
    private WorkoutCodeVwRepository workoutCodeVwRepository;

    public HashMap<String, Object> WorkoutCodeVwMap() {
        HashMap<String, Object> resultMap = new HashMap<>();
        List<WorkoutCodeVw> resultList = workoutCodeVwRepository.findAll();

        for (int i = 0; i < resultList.size(); i++) {
            HashMap<String, Object> tmpMap = new HashMap<>();
            HashMap<String, Object> tmpMap2 = new HashMap<>();

            tmpMap.put("WorkoutName", resultList.get(i).getWcCommonNm());
            tmpMap.put("WorkoutMuscle", resultList.get(i).getWcCommonMuscle());
            tmpMap.put("WorkoutType", resultList.get(i).getWcCommonType());
            tmpMap.put("WorkoutMax", null);
            tmpMap.put("WorkoutRank", null);

            tmpMap2.put(resultList.get(i).getWcCommonCode(), tmpMap);
            resultMap.putAll(tmpMap2);
        }

        return resultMap;
    }

    public HashMap<String, Object> WorkoutCodeVwLoginMap() {
        HashMap<String, Object> resultMap = new HashMap<>();
        List<WorkoutCodeVw> resultList = workoutCodeVwRepository.findAll();
        ArrayList<String> workoutLoginCodeList  = new ArrayList<>();

        for (int i = 0; i < resultList.size(); i++) {
            HashMap<String, Object> tmpMap = new HashMap<>();
            HashMap<String, Object> tmpMap2 = new HashMap<>();
            String commonCode = resultList.get(i).getWcCommonCode();

            if(commonCode.contains("01")){
                workoutLoginCodeList.add(commonCode);
                tmpMap.put("WorkoutName", resultList.get(i).getWcCommonNm());
                tmpMap.put("WorkoutMuscle", resultList.get(i).getWcCommonMuscle());
                tmpMap.put("WorkoutType", resultList.get(i).getWcCommonType());
                tmpMap.put("WorkoutMax", null);
                tmpMap.put("WorkoutRank", null);
                tmpMap2.put(resultList.get(i).getWcCommonCode(), tmpMap);
                resultMap.putAll(tmpMap2);
            }
//
//            tmpMap2.put(resultList.get(i).getWcCommonCode(), tmpMap);
//            resultMap.putAll(tmpMap2);
        }
        resultMap.put("LoginWorkoutCode", workoutLoginCodeList);
        return resultMap;
    }
}
