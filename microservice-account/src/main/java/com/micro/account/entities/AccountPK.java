package com.micro.account.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Objeto que representa la llave primaria (compuesta) de una cuenta
 */
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class AccountPK implements Serializable {
    private Long clientId;
    private Long nroCuenta;
    private String tipoCuenta;
}