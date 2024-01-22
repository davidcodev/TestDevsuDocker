package com.micro.account.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Se usa para presentar los datos de la cuenta en el endpoint de consulta
 * incluye el nombre del cliente en lugar de su Id
 */
@Getter @Setter
public class AccountDTO {
    private Long nroCuenta;
    private String tipo;
    private BigDecimal saldoInicial;
    private Boolean estado;
    private String cliente;
}
