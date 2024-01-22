package com.micro.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Objeto usado para presentar los datos en pantalla al momento de listar (Caso de uso del ejemplo)
 */
@Getter @Setter @AllArgsConstructor
public class CustomerDTO {
    private String nombre;
    private String direccion;
    private String telefono;
    private String contrasena;
    private Boolean estado;
}
