package com.anderson.ordermanager.infra.service;

import com.anderson.ordermanager.app.gateways.EmailGateway;
import com.anderson.ordermanager.infra.events.producer.EmailProducer;
import com.anderson.ordermanager.infra.web.dto.EmailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailManagerService implements EmailGateway {
	private final EmailProducer emailProducer;

	@Override
	public void send(EmailDto email) {
		emailProducer.send(email);
	}
}
