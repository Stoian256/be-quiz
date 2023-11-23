package com.example.bequiz.controller;

import com.example.bequiz.exception.EntityValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = EntityValidationException.class)
    public ResponseEntity<String> handle(EntityValidationException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.valueOf(exception.getErrorCode().getCode()));
    }
}
