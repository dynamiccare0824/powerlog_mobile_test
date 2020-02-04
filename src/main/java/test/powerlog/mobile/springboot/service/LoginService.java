package test.powerlog.mobile.springboot.service;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.powerlog.mobile.springboot.domain.products.UserAccount;
import test.powerlog.mobile.springboot.domain.products.UserAccountRepository;
import test.powerlog.mobile.springboot.web.dto.UserAccountDto;

import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    /*로그인 요청 처리*/
    public boolean Login(String id, String password) {
        boolean result = false;
//        System.out.println(userAccountRepository.findById(id).get().getM_login_vw_email());
        try {
            System.out.println(userAccountRepository.findById(id));
            Optional<UserAccount> record = userAccountRepository.findById(id);
            // System.out.println(userAccountDto.getM_login_vw_email());

            if (record.get().getM_login_vw_email().equals(id) && record.get().getM_login_vw_password().equals(password)) {
                System.out.println(record.get().getM_login_vw_email());
                System.out.println("Correct");
                result = true;
            } else {
                System.out.println(record.get().getM_login_vw_email());
                System.out.println("Error");
                result = false;
            }//            return (record.get().getId() == testProductDto.getId() && record.get().getPassword() == testProductDto.getPassword()) ? true: false
        } catch (Exception ex) {
            System.out.println(ex);
        }
        System.out.println("Finally");
        return result;
    }
}
