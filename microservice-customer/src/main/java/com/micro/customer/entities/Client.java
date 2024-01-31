package com.micro.customer.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad Cliente que extiende de Person, su id es autoincremental
 */
@Entity
@Table(name = "client")
@Getter @Setter
public class Client extends Person {
    @Id
    @Column(name = "client_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clienteId;
    @NotEmpty(message = "no se ha encontrado")
    @Size(min = 3, max = 10, message = "debe tener una longitud entre 3 y 10 caracteres")
    private String contrasena;
    @NotNull(message = "no se ha encontrado")
    private Boolean estado;
}
