package com.anderson.ordermanager.controller.dto;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class StockMovementDto {
    private Long item_id;
    private Long quantity;
}
