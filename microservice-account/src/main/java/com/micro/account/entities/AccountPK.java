package com.micro.account.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class AccountPK implements Serializable {
    private Long clientId;
    private Long nroCuenta;
    private String tipoCuenta;
}