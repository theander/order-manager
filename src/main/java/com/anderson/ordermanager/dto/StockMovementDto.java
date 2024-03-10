package com.anderson.ordermanager.dto;

import com.anderson.ordermanager.enums.StatusEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StockMovementDto {
    @NotNull(message = "Item id is required")
    private Long itemId;
    @NotNull(message = "Item id is required")
    @Min(value = 1,message = "Minimum amount allowed is 1")
    private Long quantity;
    private StatusEnum status;
}
