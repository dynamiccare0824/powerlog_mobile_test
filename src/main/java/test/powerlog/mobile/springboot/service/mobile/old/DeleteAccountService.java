package test.powerlog.mobile.springboot.service.mobile.old;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.powerlog.mobile.springboot.domain.table.UserTbRepository;

import java.util.HashMap;

@Service
public class DeleteAccountService {

    @Autowired
    private UserTbRepository userTbRepository;

    public HashMap<String, Object> DeleteAccount(HashMap<String, Object> map, String email) {
        if ((Boolean) map.get("isMatch")){
            try {
                userTbRepository.deleteById(email);
            } catch (Exception ex) {
                map.replace("error", ex.toString());
            }
        }
        return map;
    }
}
