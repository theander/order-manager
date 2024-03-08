package com.anderson.ordermanager.controller.dto;

import lombok.Data;

import java.time.OffsetDateTime;
@Data
public class OrderDto {

    private Long userId;
    private Long itemId;
    private Long quantity;
}
