package com.micro.account.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Entidad que maneja los campos de una cuenta
 */
@Entity
@Table(name = "account")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Account {
    @EmbeddedId
    private AccountPK id;
    private BigDecimal saldoInicial;
    private Boolean estado;
}
