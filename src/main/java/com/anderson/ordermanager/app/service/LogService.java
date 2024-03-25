package com.anderson.ordermanager.app.service;

import com.anderson.ordermanager.app.exception.custom.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class LogService {

	public byte[] fetchLog() throws IOException {
		Path logFilePath = Paths.get("logs/application.log");
		if (!Files.exists(logFilePath)) {
			throw new EntityNotFoundException("Log not Found");
		}
		return Files.readAllBytes(logFilePath);
	}
}
