package com.anderson.ordermanager.exception;

import com.anderson.ordermanager.exception.custom.EntityNotFoundException;
import com.anderson.ordermanager.exception.custom.UniqueConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdviceExceptionHandler {
    @ExceptionHandler(UniqueConstraintViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleUniqueConstraintViolationException(UniqueConstraintViolationException e){
        return e.getMessage();
    }
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleEntityNotFoundException(EntityNotFoundException e){
        return e.getMessage();
    }
}
