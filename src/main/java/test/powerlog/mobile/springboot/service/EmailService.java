package test.powerlog.mobile.springboot.service;

import test.powerlog.mobile.springboot.web.dto.request.EmailFormDto;
import javax.mail.MessagingException;


public interface EmailService {
    void sendMail(EmailFormDto emailFormDto) throws MessagingException;
}
