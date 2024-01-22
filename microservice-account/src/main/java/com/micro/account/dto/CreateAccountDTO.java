package com.micro.account.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Se utiliza para modelar los datos de la creación de una cuenta
 */
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class CreateAccountDTO {
    private Long nroCuenta;
    private String tipo;
    private BigDecimal saldoInicial;
    private Boolean estado;
    private Long idCliente;
}
