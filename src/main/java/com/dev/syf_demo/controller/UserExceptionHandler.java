package com.dev.syf_demo.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler (MethodArgumentNotValidException.class)
    ResponseEntity<List<String>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex)
    {
        List<String> errors = new ArrayList<>();
        ex.getAllErrors().forEach(error -> errors.add(error.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
