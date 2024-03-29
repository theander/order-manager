package com.anderson.ordermanager.infra.events.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoggerProducer {
	private static final String ORDER_MANAGER_EXCHANGE_NAME = "order-manager";
	private static final String LOG_ROUTING_QUEUE = "log";


	private final RabbitTemplate rabbitTemplate;

	public void send(String log) {
		rabbitTemplate.convertAndSend(ORDER_MANAGER_EXCHANGE_NAME, LOG_ROUTING_QUEUE, log);
	}
}