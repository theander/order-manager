package com.anderson.ordermanager.infra.service;

import com.anderson.ordermanager.infra.web.dto.EmailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceSender {
	private final JavaMailSender mailSender;

	public void send(EmailDto email) {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom(email.getFrom());
		msg.setTo(email.getTo());
		msg.setSubject(email.getSubject());
		msg.setText(email.getBody());
		mailSender.send(msg);
	}
}
