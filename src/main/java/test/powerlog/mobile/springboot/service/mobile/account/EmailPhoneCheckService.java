package test.powerlog.mobile.springboot.service.mobile.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.powerlog.mobile.springboot.domain.view.UserAccountVw;
import test.powerlog.mobile.springboot.domain.view.UserAccountVwRepository;

import java.util.Optional;

@Service
public class EmailPhoneCheckService {

    @Autowired
    private UserAccountVwRepository userAccountVwRepository;

    public boolean emailPhoneCheck(String email, String phone) {
        boolean result = false;
        System.out.println(email + phone);
//        System.out.println(userAccountVWRepository.findById(email).get().getLoginVwEmail());
        try {
            System.out.println(userAccountVwRepository.findById(email));
            Optional<UserAccountVw> record = userAccountVwRepository.findById(email);

            if (record.get().getLoginVwEmail().equals(email) && record.get().getLoginVwPhone().equals(phone)) {
                System.out.println(record.get().getLoginVwEmail());
                System.out.println("Correct");
                result = true;
            } else {
                System.out.println(record.get().getLoginVwEmail());
                System.out.println("Error");
                result = false;
            }//            return (record.get().getId() == testProductDto.getId() && record.get().getPassword() == testProductDto.getPassword()) ? true: false
        } catch (Exception ex) {
            System.out.println(ex);
        }
        System.out.println("EmailPhoneCheckDone");
        return result;
    }
}
