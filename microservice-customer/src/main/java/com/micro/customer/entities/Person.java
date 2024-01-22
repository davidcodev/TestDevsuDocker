package com.micro.customer.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Objeto Persona (no es entidad porque así lo pide el ejercicio)
 * Por esta razón se anota con MappedSuperclass, para que información de mapeo se aplique a las entidades
 * que heredan de esta
 */
@MappedSuperclass
@Getter @Setter
public class Person {
    @Column(unique = true)
    private String identificacion;
    private String nombre;
    private String genero;
    private int edad;
    private String direccion;
    private String telefono;
}
