package test.powerlog.mobile.springboot.service;

import org.springframework.stereotype.Service;
import test.powerlog.mobile.springboot.web.dto.EmailFormDto;
import javax.mail.MessagingException;


public interface EmailService {
    void sendMail(EmailFormDto emailFormDto) throws MessagingException;
}
