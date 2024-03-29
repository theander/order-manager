package com.anderson.ordermanager.infra.events.consumer;

import com.anderson.ordermanager.infra.service.EmailServiceSender;
import com.anderson.ordermanager.infra.web.dto.EmailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailConsumer {
	private static final String EMAIL_QUEUE = "email-queue";
	private final EmailServiceSender emailServiceSender;

	@RabbitListener(queues = EMAIL_QUEUE)
	public void listener(EmailDto emailDto) {
		emailServiceSender.send(emailDto);
	}
}
