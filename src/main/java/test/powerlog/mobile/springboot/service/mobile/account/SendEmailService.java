package test.powerlog.mobile.springboot.service.mobile.account;

import org.springframework.stereotype.Service;
import test.powerlog.mobile.springboot.web.dto.mobile.request.account.EmailFormDto;
import javax.mail.MessagingException;

@Service
public interface SendEmailService {
    void sendMail(EmailFormDto emailFormDto) throws MessagingException;
}
