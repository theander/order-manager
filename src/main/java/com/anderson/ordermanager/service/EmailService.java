package com.anderson.ordermanager.service;

import com.anderson.ordermanager.dto.EmailDto;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(EmailDto email) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(email.getFrom());
        msg.setTo(email.getTo());
        msg.setSubject(email.getSubject());
        msg.setText(email.getBody());
        mailSender.send(msg);
    }

}
