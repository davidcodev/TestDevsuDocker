package com.micro.customer.controllers;

import com.micro.customer.models.ErrorModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ErrorHandlerException {
    /**
     * Excepción para cuando se accede a un endponint no configurado
     * @param e Tipo de Excepción
     * @return Objeto de tipo ErrorModel con el contenido del error
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorModel> ex(NoHandlerFoundException e) {
        ErrorModel error = new ErrorModel(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND, "No se ha configurado el Endpoint");
        return ResponseEntity.status(404).body(error);
    }

    /**
     * Excepción para cuando se ingresa en una trama un tipo de dato que no corresponde
     * @param e Tipo de Excepción
     * @return Objeto de tipo ErrorModel con el contenido del error
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorModel> ex1(HttpMessageNotReadableException e) {
        ErrorModel error = new ErrorModel(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND, "Existe algún inconveniente con los campos de la trama");
        return ResponseEntity.status(404).body(error);
    }
}
