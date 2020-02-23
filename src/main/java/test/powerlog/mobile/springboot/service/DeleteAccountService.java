package test.powerlog.mobile.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.powerlog.mobile.springboot.domain.products.UserTbRepository;

@Service
public class DeleteAccountService {

    @Autowired
    private UserTbRepository userTbRepository;

    public void DeleteAccount(String email){
        try{userTbRepository.deleteById(email);}
        catch(Exception ex) {
        }
    }
}
