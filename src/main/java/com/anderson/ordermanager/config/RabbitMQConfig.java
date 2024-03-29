package com.anderson.ordermanager.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

	private static final String EMAIL_QUEUE_NAME = "email-queue";
	private static final String LOG_QUEUE_NAME = "log-queue";
	private static final String EMAIL_ROUTING_QUEUE = "email";
	private static final String LOG_ROUTING_QUEUE = "log";
	private static final String ORDER_MANAGER_EXCHANGE_NAME = "order-manager";

	@Bean
	public Queue emailQueue() {
		return new Queue(EMAIL_QUEUE_NAME, true);
	}

	@Bean
	public Queue logQueue() {
		return new Queue(LOG_QUEUE_NAME, true);
	}

	@Bean
	public DirectExchange orderManagerExchange() {
		return new DirectExchange(ORDER_MANAGER_EXCHANGE_NAME);
	}

	@Bean
	public Binding emailBinding(Queue emailQueue, DirectExchange orderManagerExchange) {
		return BindingBuilder.bind(emailQueue).to(orderManagerExchange).with(EMAIL_ROUTING_QUEUE);
	}

	@Bean
	public Binding logBinding(Queue logQueue, DirectExchange orderManagerExchange) {
		return BindingBuilder.bind(logQueue).to(orderManagerExchange).with(LOG_ROUTING_QUEUE);
	}

	@Bean
	public MessageConverter converter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public AmqpTemplate template(ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(converter());
		return rabbitTemplate;
	}
}