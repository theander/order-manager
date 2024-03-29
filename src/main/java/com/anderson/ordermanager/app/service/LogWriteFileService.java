package com.anderson.ordermanager.app.service;

import com.anderson.ordermanager.app.gateways.LogWriteFileGateway;

public class LogWriteFileService {
	private final LogWriteFileGateway logWriteFileGateway;

	public LogWriteFileService(LogWriteFileGateway logWriteFileGateway) {
		this.logWriteFileGateway = logWriteFileGateway;
	}

	public void writeLog(String message) {
		logWriteFileGateway.write(message);
	}
}
