package com.anderson.ordermanager.app.service;

import com.anderson.ordermanager.app.exception.custom.EntityNotFoundException;
import com.anderson.ordermanager.app.gateways.LogFetchFileGateway;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class LogFetchFileService {
	private final LogFetchFileGateway logGateway;

	public LogFetchFileService(LogFetchFileGateway logGateway) {
		this.logGateway = logGateway;
	}

	public byte[] fetchLogFile() throws IOException {
		Path logFilePath = logGateway.fetchPath("logs/application.log");
		if (!Files.exists(logFilePath)) {
			throw new EntityNotFoundException("Log not Found");
		}
		return Files.readAllBytes(logFilePath);
	}
}
