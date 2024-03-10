package com.anderson.ordermanager.exception.custom;

public class UniqueConstraintViolationException  extends RuntimeException {
    public UniqueConstraintViolationException(String message) {
        super(message);
    }
}
