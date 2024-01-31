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

/**
 * Se utiliza para modelar los datos de la creación de una cuenta
 */
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class CreateAccountDTO {
    @NotNull(message = "no se ha encontrado")
    private Long nroCuenta;
    @NotEmpty(message = "nose ha encontrado")
    @Pattern(regexp = "^(AHORROS|CORRIENTE)$", message = "solo puede ser AHORROS o CORRIENTE")
    private String tipo;
    @NotNull(message = "no se ha encontrado")
    private BigDecimal saldoInicial;
    @NotNull(message = "no se ha encontrado")
    private Boolean estado;
    @NotNull(message = "no se ha encontrado")
    private Long idCliente;
}
