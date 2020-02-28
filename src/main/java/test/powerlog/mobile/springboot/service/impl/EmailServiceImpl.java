package test.powerlog.mobile.springboot.service.impl;

import test.powerlog.mobile.springboot.web.dto.request.EmailFormDto;
import test.powerlog.mobile.springboot.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;



@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendMail(EmailFormDto email) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        message.setSubject(email.getSubject());
        message.setSender(new InternetAddress(email.getSender()));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(email.getRecipient()));
        message.setText(email.getContent());
        message.setSentDate(new Date());

        javaMailSender.send(message);
    }
}
