package test.powerlog.mobile.springboot.service.mobile.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.powerlog.mobile.springboot.domain.view.UserAccountVwRepository;
import test.powerlog.mobile.springboot.domain.table.UserTb;
import test.powerlog.mobile.springboot.domain.table.UserTbRepository;
import test.powerlog.mobile.springboot.service.mobile.account.SendEmailService;
import test.powerlog.mobile.springboot.service.mobile.account.SignUpService;
import test.powerlog.mobile.springboot.web.dto.mobile.request.account.SignUpDto;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ResetUidService {

    @Autowired
    private UserAccountVwRepository userAccountVwRepository;

    @Autowired
    private UserTbRepository userTbRepository;

    @Autowired
    private SendEmailService sendEmailService;

    @Autowired
    private SignUpService signUpService;

    /*로그인 요청 처리*/
    public boolean ResetUid(String email, String randNum) {
        boolean result = false;
        try {
            LocalDateTime localDateTime = LocalDateTime.now();

            Optional<UserTb> record = userTbRepository.findById(email);

            SignUpDto signUpDto  = SignUpDto.builder().email(record.get().getUEmail()).password(record.get().getUPassword()).uid(randNum).name(record.get().getUName())
                    .gender(record.get().getUGender()).birth(record.get().getUBirth()).height(record.get().getUHeight()).weight(record.get().getUWeight())
                    .agreeFlag(record.get().getUAgreeFlag()).personalFlag(record.get().getUAgreeFlag()).shapeCode(record.get().getUShapeCode()).qAnswer(record.get().getUQAnswer()).qCode(record.get().getUQCode())
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
