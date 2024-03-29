package com.anderson.ordermanager.infra.web.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderDto {
	@NotNull(message = "User id is required")
	private Long userId;
	@NotNull(message = "Item id is required")
	private Long itemId;
	@NotNull(message = "Item id is required")
	@Min(value = 1, message = "Minimum amount allowed is 1")
	private Long quantity;
}
