package com.micro.customer.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Objeto usado para setear los campos de un cliente
 */
@Getter @Setter
public class CustomerUpdateDTO {
    @NotEmpty(message = "no se ha encontrado")
    private String nombre;
    @NotEmpty(message = "no se ha encontrado")
    private String genero;
    @NotNull(message = "no se ha encontrado")
    @Min(value = 18, message = "debe ser mayor o igual a 18")
    private Integer edad;
    @NotEmpty(message = "no se ha encontrado")
    private String direccion;
    @NotEmpty(message = "no se ha encontrado")
    private String telefono;
    @NotEmpty(message = "no se ha encontrado")
    @Size(min = 3, max = 10, message = "debe tener una longitud entre 3 y 10 caracteres")
    private String contrasena;
    @NotNull(message = "no se ha encontrado")
    private Boolean estado;
}
