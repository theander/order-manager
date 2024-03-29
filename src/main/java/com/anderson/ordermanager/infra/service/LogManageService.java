package com.anderson.ordermanager.infra.service;

import com.anderson.ordermanager.app.gateways.LogFetchFileGateway;
import com.anderson.ordermanager.app.gateways.LogWriteFileGateway;
import com.anderson.ordermanager.infra.events.producer.LoggerProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class LogManageService implements LogFetchFileGateway, LogWriteFileGateway {
	private final LoggerProducer loggerProducer;

	@Override
	public Path fetchPath(String path) {
		return Paths.get(path);
	}

	@Override
	public void write(String message) {
		loggerProducer.send(message);
	}
}
