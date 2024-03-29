package com.anderson.ordermanager.infra.events.consumer;

import com.anderson.ordermanager.infra.service.LogWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoggerConsumer {
	private static final String LOG_QUEUE = "log-queue";
	private final LogWriteService logWriteService;

	@RabbitListener(queues = LOG_QUEUE)
	public void listener(String log) {
		logWriteService.write(log);
	}
}
