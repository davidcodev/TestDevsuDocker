package com.micro.customer.entities;

import jakarta.persistence.*;
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
    private String contrasena;
    private Boolean estado;
}
