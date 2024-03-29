package com.anderson.ordermanager.infra.events.producer;

import com.anderson.ordermanager.infra.web.dto.EmailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailProducer {
	private static final String ORDER_MANAGER_EXCHANGE_NAME = "order-manager";
	private static final String EMAIL_ROUTING_QUEUE = "email";


	private final RabbitTemplate rabbitTemplate;

	public void send(EmailDto emailDto) {
		rabbitTemplate.convertAndSend(ORDER_MANAGER_EXCHANGE_NAME, EMAIL_ROUTING_QUEUE, emailDto);

	}
}
