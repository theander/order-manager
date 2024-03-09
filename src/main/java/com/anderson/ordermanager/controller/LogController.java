package com.anderson.ordermanager.controller;

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
    @GetMapping("log")
    public ResponseEntity<byte[]> downloadLog() throws IOException {
        Path logFilePath = Paths.get("logs/application.log");
        if (!Files.exists(logFilePath)) {
            return ResponseEntity.notFound().build();
        }
        byte[] logContent = Files.readAllBytes(logFilePath);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=\"application.log\"")
                .body(logContent);
    }
}
