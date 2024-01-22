package com.micro.customer.controllers;

import com.micro.customer.models.ErrorModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ErrorHandlerException {
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorModel> ex(NoHandlerFoundException e) {
        ErrorModel error = new ErrorModel(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND, "No se ha configurado el Endpoint");
        return ResponseEntity.status(404).body(error);
    }
}
