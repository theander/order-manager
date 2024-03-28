package com.anderson.ordermanager.app.service;

import com.anderson.ordermanager.app.gateways.EmailGateway;
import com.anderson.ordermanager.infra.web.dto.EmailDto;


public class EmailService {
	private final EmailGateway emailGateway;

	public EmailService(EmailGateway emailGateway) {
		this.emailGateway = emailGateway;
	}

	public void sendEmail(EmailDto email) {
		emailGateway.send(email);
	}
}
