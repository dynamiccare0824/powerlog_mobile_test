package test.powerlog.mobile.springboot.service.mobile.old;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.powerlog.mobile.springboot.domain.view.UserAccountVw;
import test.powerlog.mobile.springboot.domain.view.UserAccountVwRepository;
import test.powerlog.mobile.springboot.service.mobile.impl.SendEmailServiceImpl;
import test.powerlog.mobile.springboot.web.dto.mobile.request.EmailFormDto;

import java.util.Optional;

@Service
public class EmailQuestionCheckService {

    @Autowired
    private UserAccountVwRepository userAccountVwRepository;

    @Autowired
    private SendEmailServiceImpl emailServiceImpl;

    @Autowired
    private ResetPasswordService resetPasswordService;

    /*로그인 요청 처리*/
    public boolean emailQuestionCheck(String email, String questionCode, String questionAnswer, String number) {
        boolean result = false;
        try {
            System.out.println(userAccountVwRepository.findById(email));
            Optional<UserAccountVw> record = userAccountVwRepository.findById(email);
            String name = record.get().getLoginVwName();

            if (record.get().getLoginVwQCode().equals(questionCode) && record.get().getLoginVwQAnswer().equals(questionAnswer)) {
                System.out.println(record.get().getLoginVwEmail());
                System.out.println("Correct");

                EmailFormDto emailFormDto = new EmailFormDto();
                emailFormDto.setSender("noreply.dynamiccare@gmail.com");
                emailFormDto.setContent("파워로그 임시 비밀번호 " + number + " 입니다. \n로그인 후 비밀번호 재설정을 통해 비밀번호를 변경할 수 있습니다." +
                        "\n이 이메일 주소는 발신 전용 주소입니다. 회신이 불가능합니다." );
                emailFormDto.setRecipient(email);
                emailFormDto.setSubject("[파워로그] " + name + " 고객님, 안녕하세요! 발급된 임시 비밀번호를 확인하세요");
                emailServiceImpl.sendMail(emailFormDto);

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
