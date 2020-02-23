package test.powerlog.mobile.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.powerlog.mobile.springboot.domain.products.UserAccountVwRepository;
import test.powerlog.mobile.springboot.domain.products.UserTb;
import test.powerlog.mobile.springboot.domain.products.UserTbRepository;
import test.powerlog.mobile.springboot.web.dto.SignUpDto;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ResetShapeCodeService {

    @Autowired
    private UserAccountVwRepository userAccountVwRepository;

    @Autowired
    private UserTbRepository userTbRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private SignUpService signUpService;

    /*로그인 요청 처리*/
    public boolean ResetShapeCode(String email, String shapeCode) {
        boolean result = false;
        try {
            LocalDateTime localDateTime = LocalDateTime.now();
            Optional<UserTb> record = userTbRepository.findById(email);

            SignUpDto signUpDto  = SignUpDto.builder().email(record.get().getUEmail()).password(record.get().getUEmail()).uid(record.get().getUUid()).name(record.get().getUName())
                    .gender(record.get().getUGender()).birth(record.get().getUBirth()).height(record.get().getUHeight()).weight(record.get().getUWeight())
                    .agreeFlag(record.get().getUAgreeFlag()).personalFlag(record.get().getUAgreeFlag()).shapeCode(shapeCode).qAnswer(record.get().getUQAnswer()).qCode(record.get().getUQCode())
                    .verification(record.get().getUVerification()).phone(record.get().getUPhone())
                    .createdTime(record.get().getUCreatedTime()).updatedTime(localDateTime).career(record.get().getUCareer()).build();
            signUpService.Signup(signUpDto); // save 실행
            System.out.println(record.get().getUEmail());
            signUpService.Signup(signUpDto); // save 실행
            System.out.println("Correct");
            result = true;
        } catch (Exception ex) {
            System.out.println(ex);
        }
        System.out.println("EmailQuestionCheckDone");
        return result;
    }
}
