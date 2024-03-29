package com.anderson.ordermanager.infra.service;

import com.anderson.ordermanager.infra.events.consumer.LoggerConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LogWriteService {
	private static final Logger logger = LoggerFactory.getLogger(LoggerConsumer.class);

	public void write(String msg) {
		logger.info(msg);
	}

}
