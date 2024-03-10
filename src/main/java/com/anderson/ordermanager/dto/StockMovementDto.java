package com.anderson.ordermanager.dto;

import com.anderson.ordermanager.enums.StatusEnum;
import lombok.Data;

@Data
public class StockMovementDto {
    private Long itemId;
    private Long quantity;
    private StatusEnum status;
}
