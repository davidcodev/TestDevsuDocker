package com.micro.account.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter @Setter @AllArgsConstructor
public class ErrorModel {
    private Integer code;
    private HttpStatus status;
    private String message;
}
