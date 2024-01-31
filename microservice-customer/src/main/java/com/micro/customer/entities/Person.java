package com.micro.customer.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
    @NotEmpty(message = "no se ha encontrado")
    @Size(min = 10, max = 10, message = "debe tener 10 caracteres")
    @Pattern(regexp = "^\\d+$", message = "debe contener números únicamente")
    private String identificacion;
    @NotEmpty(message = "no se ha encontrado")
    private String nombre;
    @NotEmpty(message = "no se ha encontrado")
    private String genero;
    @NotNull(message = "no se ha encontrado")
    @Min(value = 18, message = "debe ser mayor o igual a 18")
    private int edad;
    @NotEmpty(message = "no se ha encontrado")
    private String direccion;
    @NotEmpty(message = "no se ha encontrado")
    private String telefono;
}
