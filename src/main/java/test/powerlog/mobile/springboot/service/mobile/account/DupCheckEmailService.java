package test.powerlog.mobile.springboot.service.mobile.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.powerlog.mobile.springboot.domain.view.UserAccountVwRepository;

import java.util.HashMap;

@Service
public class DupCheckEmailService {

    @Autowired
    private UserAccountVwRepository userAccountVwRepository;

    public HashMap<String, Object> DupCheckEmail(String email) {
        HashMap<String, Object> tmpMap = new HashMap<>();
        if (userAccountVwRepository.findById(email).isPresent()) {
            tmpMap.put("emailPresent", true);
            tmpMap.put("error", null);
        } else {
            tmpMap.put("emailPresent", false);
            tmpMap.put("error", null);
        }
        return tmpMap;
    }


}
