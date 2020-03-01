package test.powerlog.mobile.springboot.service.mobile;

import org.springframework.stereotype.Service;
import test.powerlog.mobile.springboot.web.dto.mobile.request.EmailFormDto;
import javax.mail.MessagingException;

@Service
public interface SendEmailService {
    void sendMail(EmailFormDto emailFormDto) throws MessagingException;
}