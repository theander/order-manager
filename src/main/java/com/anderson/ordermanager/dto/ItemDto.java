package com.anderson.ordermanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ItemDto {
    @NotNull(message = "Name is required")
    @NotBlank(message = "Name must not be empty")
    private String name;
}
