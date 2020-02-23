package test.powerlog.mobile.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.powerlog.mobile.springboot.domain.products.UserAccountVwRepository;
import test.powerlog.mobile.springboot.domain.products.UserTb;
import test.powerlog.mobile.springboot.domain.products.UserTbRepository;
import test.powerlog.mobile.springboot.web.dto.SignUpDto;

import java.util.Optional;

@Service
public class ResetPhoneService {
    @Autowired
    private UserTbRepository userTbRepository;

    @Autowired
    private SignUpService signUpService;

    /*로그인 요청 처리*/
    public boolean ResetPhone(String email, String phone) {
        boolean result = false;
        try {

            Optional<UserTb> record = userTbRepository.findById(email);

            SignUpDto signUpDto  = SignUpDto.builder().email(record.get().getUEmail()).password(record.get().getUEmail()).uid(record.get().getUUid()).name(record.get().getUName())
                    .gender(record.get().getUGender()).birth(record.get().getUBirth()).height(record.get().getUHeight()).weight(record.get().getUWeight())
                    .agreeFlag(record.get().getUAgreeFlag()).personalFlag(record.get().getUAgreeFlag()).shapeCode(record.get().getUShapeCode()).qAnswer(record.get().getUQAnswer()).qCode(record.get().getUQCode())
                    .verification(record.get().getUVerification()).phone(phone)
                    .createdTime(record.get().getUCreatedTime()).updatedTime(record.get().getUUpdatedTime()).career(record.get().getUCareer()).build();
            signUpService.Signup(signUpDto); // save 실행
            System.out.println("Correct");
            result = true;
        } catch (Exception ex) {
            System.out.println(ex);
        }
        System.out.println("ResetPhone");
        return result;
    }
}
