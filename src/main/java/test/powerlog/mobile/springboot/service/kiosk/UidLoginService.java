package test.powerlog.mobile.springboot.service.kiosk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.powerlog.mobile.springboot.domain.table.UserTbRepository;
import test.powerlog.mobile.springboot.domain.view.UserAccountVw;
import test.powerlog.mobile.springboot.domain.view.UserAccountVwRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class UidLoginService {
    @Autowired
    private UserAccountVwRepository userAccountVwRepository;

    public HashMap<String, Object> getUidLoginResult(HashMap<String, Object> uidLoginResult, String uid) {
        Optional<UserAccountVw> record = Optional.ofNullable(userAccountVwRepository.findByLoginVwUid(uid));
        if (record.isPresent()) {
            uidLoginResult.put("isPresent", true);
            uidLoginResult.put("email", record.get().getLoginVwEmail());
            uidLoginResult.put("name", record.get().getLoginVwName());
        } else {
            uidLoginResult.put("isPresent", false);
            uidLoginResult.put("email", null);
            uidLoginResult.put("name", null);
        }
        uidLoginResult.put("error", null);
        return uidLoginResult;
    }
}
