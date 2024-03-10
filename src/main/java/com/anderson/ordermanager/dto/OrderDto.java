package com.anderson.ordermanager.dto;

import com.anderson.ordermanager.enums.StatusEnum;
import lombok.Data;
@Data
public class OrderDto {

    private Long userId;
    private Long itemId;
    private Long quantity;
    private StatusEnum statusEnum;
}
