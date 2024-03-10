package com.anderson.ordermanager.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EmailDto {
    private String from;
    private String to;
    private String subject;
    private String body;
}
