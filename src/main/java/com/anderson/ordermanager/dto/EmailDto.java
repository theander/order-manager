package com.anderson.ordermanager.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EmailDto {
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email address")
    private String from;
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email address")
    private String to;
    @NotBlank(message = "Subject is required")
    private String subject;
    @NotBlank(message = "Body is required")
    private String body;
}
