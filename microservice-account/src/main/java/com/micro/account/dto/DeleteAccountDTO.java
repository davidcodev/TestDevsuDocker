package com.micro.account.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Se utiliza para modelar los datos para eliminar una cuenta
 */
@Getter @Setter
public class DeleteAccountDTO {
    private Long nroCuenta;
    private String tipo;
}
