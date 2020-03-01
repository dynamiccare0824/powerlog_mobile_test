package test.powerlog.mobile.springboot.service.mobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.powerlog.mobile.springboot.domain.view.UserAccountVw;
import test.powerlog.mobile.springboot.domain.view.UserAccountVwRepository;

import java.util.HashMap;
import java.util.Optional;

@Service
public class EmailPasswordCheckService {

    @Autowired
    private UserAccountVwRepository userAccountVwRepository;

    /*로그인 요청 처리*/
    public HashMap<String, Object> EmailPasswordCheck(String email, String password) {

        HashMap<String, Object> tmpMap = new HashMap();
        Optional<UserAccountVw> record = userAccountVwRepository.findById(email);

        if(record.isPresent()){
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
        else{
            tmpMap.put("isMatch", false);
            tmpMap.put("error", "record.isPresent(): false");
            tmpMap.put("name", null);
        }
        return tmpMap;
    }
}
