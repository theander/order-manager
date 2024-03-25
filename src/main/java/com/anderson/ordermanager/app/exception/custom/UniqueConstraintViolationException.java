package com.anderson.ordermanager.app.exception.custom;

public class UniqueConstraintViolationException  extends RuntimeException {
    public UniqueConstraintViolationException(String message) {
        super(message);
    }
}
