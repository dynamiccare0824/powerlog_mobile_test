package test.powerlog.mobile.springboot.service;

import org.springframework.stereotype.Service;
import test.powerlog.mobile.springboot.web.dto.request.EmailFormDto;
import javax.mail.MessagingException;

@Service
public interface SendEmailService {
    void sendMail(EmailFormDto emailFormDto) throws MessagingException;
}
