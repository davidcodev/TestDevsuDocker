package com.micro.account.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UpdateAccountDTO {
    private Long id;
    private Long nroCuenta;
    private String tipo;
    private BigDecimal saldoInicial;
    private Boolean estado;
}
