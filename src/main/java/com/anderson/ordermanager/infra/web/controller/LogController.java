package com.anderson.ordermanager.infra.web.controller;

import com.anderson.ordermanager.app.service.LogFetchFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class LogController {
	private final LogFetchFileService logService;

	@GetMapping("log")
	public ResponseEntity<byte[]> downloadLog() throws IOException {
		byte[] logContent = logService.fetchLogFile();
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header("Content-Disposition", "attachment; filename=\"application.log\"")
				.body(logContent);
	}
}
