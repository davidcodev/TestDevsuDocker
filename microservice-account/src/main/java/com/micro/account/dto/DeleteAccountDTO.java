package com.micro.account.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

/**
 * Objeto que se utiliza para modelar los datos para eliminar una cuenta
 */
@Getter @Setter
public class DeleteAccountDTO {
    @NotNull(message = "no se ha encontrado")
    private Long nroCuenta;
    @NotEmpty(message = "no se ha encontrado")
    @Pattern(regexp = "^(AHORROS|CORRIENTE)$", message = "solo puede ser AHORROS o CORRIENTE")
    private String tipo;
}
