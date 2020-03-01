package test.powerlog.mobile.springboot.service.mobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.powerlog.mobile.springboot.domain.view.UserAccountVw;
import test.powerlog.mobile.springboot.domain.view.UserAccountVwRepository;

import java.util.HashMap;
import java.util.Optional;

@Service
public class DupCheckPhoneService {
    @Autowired
    private UserAccountVwRepository userAccountVwRepository;

    public HashMap<String, Object> DupCheckPhone(String phone){
        HashMap<String, Object> tmpMap = new HashMap<>();

        Optional<UserAccountVw> record = Optional.ofNullable(userAccountVwRepository.findByLoginVwPhone(phone));
        if (record.isPresent()) {
            tmpMap.put("phonePresent", true);
            tmpMap.put("error", null);
        } else {
            tmpMap.put("phonePresent", false);
            tmpMap.put("error", null);
        }
        return tmpMap;
    }
}
