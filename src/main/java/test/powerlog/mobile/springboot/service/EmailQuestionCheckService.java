package test.powerlog.mobile.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.powerlog.mobile.springboot.domain.products.UserAccountVw;
import test.powerlog.mobile.springboot.domain.products.UserAccountVwRepository;
import test.powerlog.mobile.springboot.web.dto.EmailDto;

import java.util.Optional;

@Service
public class EmailQuestionCheckService {

    @Autowired
    private UserAccountVwRepository userAccountVwRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ResetPasswordService resetPasswordService;

    /*로그인 요청 처리*/
    public boolean emailQuestionCheck(String email, String questionCode, String questionAnswer, String number) {
        boolean result = false;
        try {
            System.out.println(userAccountVwRepository.findById(email));
            Optional<UserAccountVw> record = userAccountVwRepository.findById(email);
            String name = record.get().getLoginVwName();

            if (record.get().getLoginVwEmail().equals(email) && record.get().getLoginVwQCode().equals(questionCode)
            && record.get().getLoginVwQAnswer().equals(questionAnswer)) {
                System.out.println(record.get().getLoginVwEmail());
                System.out.println("Correct");

                EmailDto emailDto = new EmailDto();
                emailDto.setSender("noreply.dynamiccare@gmail.com");
                emailDto.setContent("파워로그 임시 비밀번호 " + number + " 입니다. \n로그인 후 비밀번호 재설정을 통해 비밀번호를 변경할 수 있습니다." +
                        "\n이 이메일 주소는 발신 전용 주소입니다. 회신이 불가능합니다." );
                emailDto.setRecipient(email);
                emailDto.setSubject("[파워로그] " + name + " 고객님, 안녕하세요! 발급된 임시 비밀번호를 확인하세요");
                emailService.sendMail(emailDto);

                resetPasswordService.ResetPassword(email, number);
                result = true;
            } else {
                System.out.println(record.get().getLoginVwEmail());
                System.out.println("Error");
                result = false;
            }//            return (record.get().getId() == testProductDto.getId() && record.get().getPassword() == testProductDto.getPassword()) ? true: false
        } catch (Exception ex) {
            System.out.println(ex);
        }
        System.out.println("EmailQuestionCheckDone");
        return result;
    }
}
