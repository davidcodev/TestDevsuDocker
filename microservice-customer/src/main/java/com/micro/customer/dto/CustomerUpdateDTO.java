package com.micro.customer.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Objeto usado para setear los campos de un cliente que se eliminará
 */
@Getter @Setter
public class CustomerUpdateDTO {
    private String nombre;
    private String genero;
    private Integer edad;
    private String direccion;
    private String telefono;
    private String contrasena;
    private Boolean estado;
}
