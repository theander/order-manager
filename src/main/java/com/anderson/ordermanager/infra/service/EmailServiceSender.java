package com.anderson.ordermanager.infra.service;

import com.anderson.ordermanager.app.gateways.EmailGateway;
import com.anderson.ordermanager.infra.web.dto.EmailDto;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceSender implements EmailGateway {
	private final JavaMailSender mailSender;

	public EmailServiceSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void send(EmailDto email){
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom(email.getFrom());
		msg.setTo(email.getTo());
		msg.setSubject(email.getSubject());
		msg.setText(email.getBody());
		mailSender.send(msg);
	}
}
