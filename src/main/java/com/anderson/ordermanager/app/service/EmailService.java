package com.anderson.ordermanager.app.service;

import com.anderson.ordermanager.infra.service.EmailServiceSender;
import com.anderson.ordermanager.infra.web.dto.EmailDto;


public class EmailService {
	private final EmailServiceSender mailSender;
	public EmailService(EmailServiceSender mailSender) {
		this.mailSender = mailSender;
	}

	public void sendEmail(EmailDto email) {
		mailSender.send(email);
	}
}
