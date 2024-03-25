package com.anderson.ordermanager.app.entity;

import lombok.Getter;

public enum StatusEnum {
    CREATED("CREATED"),
    PENDING("PENDING"),
    DONE("DONE");

    @Getter
    private final String value;

    StatusEnum(String value) {
        this.value = value;
    }

}
