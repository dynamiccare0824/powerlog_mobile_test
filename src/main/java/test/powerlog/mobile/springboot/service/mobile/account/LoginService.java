package test.powerlog.mobile.springboot.service.mobile.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.powerlog.mobile.springboot.domain.view.LogLateMsrVw;
import test.powerlog.mobile.springboot.domain.view.LogLateMsrVwRepository;
import test.powerlog.mobile.springboot.domain.view.UserAccountVw;
import test.powerlog.mobile.springboot.domain.view.UserAccountVwRepository;

import java.util.*;
import java.util.Random;

@Service
public class LoginService {

    @Autowired
    private UserAccountVwRepository userAccountVwRepository;

    @Autowired
    private LogLateMsrVwRepository logLateMsrVwRepository;

    /*로그인 요청 처리*/
    public HashMap<String, Object> EmailPasswordCheck(String email, String password) {

        HashMap<String, Object> tmpMap = new HashMap();
        Optional<UserAccountVw> record = userAccountVwRepository.findById(email);

        if (record.isPresent()) {
            // 레코드가 존재하는데 조건에 맞다면 제대로 입력한 것
            if (record.get().getLoginVwEmail().equals(email) && record.get().getLoginVwPassword().equals(password)) {
                String name = record.get().getLoginVwName();
                tmpMap.put("isMatch", true);
                tmpMap.put("error", null);
                tmpMap.put("name", name);
            }
            // 레코드가 존재하는데 조건에 맞지 않는다면 잘못된 등록 정보라는 것
            else {
                tmpMap.put("isMatch", false);
                tmpMap.put("error", null);
                tmpMap.put("name", null);
            }
        }
        //레코드가 존재하지 않는다면 잘못된 등록 정보라는 것
        else {
            tmpMap.put("isMatch", false);
            tmpMap.put("error", "record.isPresent(): false");
            tmpMap.put("name", null);
        }
        return tmpMap;
    }

    public HashMap<String, Object> LgLateMsrVwEmailMap(String email, HashMap<String, Object> workoutCodeMap) {
        Random random = new Random();
        HashMap<String, Object> resultMap = new HashMap<>();
        Optional<List<LogLateMsrVw>> logList = Optional.ofNullable(logLateMsrVwRepository.findAllByLgLateMsrVwEmail(email));

        if (logList.isPresent()) {
            for (int i = 0; i < logList.get().size(); i++) {
                HashMap<String, Object> tmpMap = new HashMap<>();
                HashMap<String, Object> tmpMap2 = new HashMap<>();
                String commonCode = logList.get().get(i).getLgLateMsrVwCommonCode();
                HashMap<String, Object> codeDetailMap = (HashMap<String, Object>) workoutCodeMap.get((String) commonCode);
                codeDetailMap.replace("WorkoutMax", logList.get().get(i).getLgLateMsrVwMax());
                codeDetailMap.put("WorkoutRank", "왜 안나오는 거야");
                workoutCodeMap.replace("commonCode", codeDetailMap);

//                tmpMap.put("WorkoutMax", logList.get(i).getLgLateMsrVwMax());
//                tmpMap.put("Rank", random.nextInt(100));
//
//                tmpMap2.put(logList.get().get(i).getLgLateMsrVwCommonCode(), tmpMap);
//                resultMap.putAll(tmpMap2);
            }
        }
        return workoutCodeMap;
    }

    public static HashMap<String, ArrayList<String>> joinDic(String[] arr) {
        // ①
        HashMap<String, ArrayList<String>> dic = new HashMap<String, ArrayList<String>>();
        for (int i = 0; i < arr.length; i++) {
            String[] line = arr[i].split(",");  // ②
            String key = line[5]; //③

            ArrayList<String> list = new ArrayList<String>();
            if (dic.containsKey(key)) { //⑤
                list = dic.get(key);  //⑤-1
                list.add(line[13]);
            } else {
                list.add(line[13]); //⑤-2
            }
            dic.put(key, list); //⑥
        }
        for (String key : dic.keySet()) {  //⑦
            System.out.println(key);
        }
        return dic;
    }
}
