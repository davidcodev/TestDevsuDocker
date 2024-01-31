package com.micro.account.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Objeto que se utiliza para consumir el Endpoint de Clientes, trae todos los campos de un Cliente
 */
@Getter @Setter
public class CustomerDTO {
    private String clienteId;
    private String identificacion;
    private String nombre;
    private String genero;
    private Integer edad;
    private String direccion;
    private String telefono;
    private String contrasena;
    private Boolean estado;
}
