package test.powerlog.mobile.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.powerlog.mobile.springboot.domain.products.UserAccountVw;
import test.powerlog.mobile.springboot.domain.products.UserAccountVwRepository;

import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    private UserAccountVwRepository userAccountVWRepository;

    /*로그인 요청 처리*/
    public boolean Login(String email, String password) {
        boolean result = false;
//        System.out.println(userAccountRepository.findById(email).get().getM_login_vw_email());
        try {
            System.out.println(userAccountVWRepository.findById(email));
            Optional<UserAccountVw> record = userAccountVWRepository.findById(email);

            if (record.get().getLoginVwEmail().equals(email) && record.get().getLoginVwPassword().equals(password)) {
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
        System.out.println("Finally");
        return result;
    }

//    public boolean EmailPresent(String )
}
