package com.micro.account.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UpdateAccountDTO {
    @NotNull(message = "no se ha encontrado")
    private Long id;
    @NotNull(message = "no se ha encontrado")
    private Long nroCuenta;
    @NotEmpty(message = "no se ha encontrado")
    @Pattern(regexp = "^(AHORROS|CORRIENTE)$", message = "solo puede ser AHORROS o CORRIENTE")
    private String tipo;
    @NotNull(message = "no se ha encontrado")
    private BigDecimal saldoInicial;
    @NotNull(message = "no se ha encontrado")
    private Boolean estado;
}
