package com.micro.customer.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * Objeto para modelar un error general
 */
@Getter @Setter @AllArgsConstructor
public class ErrorModel {
    private Integer code;
    private HttpStatus status;
    private String message;
}
