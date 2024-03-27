package com.anderson.ordermanager.infra.web.controller;

import com.anderson.ordermanager.app.service.LogService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("api")
public class LogController {
    private final LogService logService;

	public LogController(LogService logService) {
		this.logService = logService;
	}

	@GetMapping("log")
    public ResponseEntity<byte[]> downloadLog() throws IOException {
        byte[] logContent =logService.fetchLog();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=\"application.log\"")
                .body(logContent);
    }
}
