package com.anderson.ordermanager.app.service;

import com.anderson.ordermanager.app.gateways.EmailGateway;
import com.anderson.ordermanager.infra.web.dto.EmailDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {
	@Mock
	EmailGateway emailGateway;
	@InjectMocks
	EmailService emailService;

	@Test
	void sendEmail() {
		EmailDto email = EmailDto.builder()
				.body("Message body")
				.from("joao@email.com")
				.to("jose@email.com")
				.subject("Information email")
				.build();
		doNothing().when(emailGateway).send(any());
		emailService.sendEmail(email);
		verify(emailGateway).send(email);
	}
}