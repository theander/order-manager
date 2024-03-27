package com.anderson.ordermanager.infra.web.dto;

public enum SortEnum {
    ASC("ASC"),
    DESC("DESC");
    private final String value;

    SortEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
