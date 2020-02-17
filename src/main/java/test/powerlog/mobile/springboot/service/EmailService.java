package test.powerlog.mobile.springboot.service;

import test.powerlog.mobile.springboot.web.dto.EmailDto;
import javax.mail.MessagingException;

public interface EmailService {
    void sendMail(EmailDto emailDto) throws MessagingException;
}
