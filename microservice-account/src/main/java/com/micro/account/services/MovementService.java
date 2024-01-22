package com.micro.account.services;

import com.micro.account.entities.Account;
import com.micro.account.entities.Movement;

import java.util.List;
import java.util.Optional;

public interface MovementService {
    List<Movement> findAll();
    List<Movement> findByNroCuenta(Long nroCuenta);
    List<Movement> findByNroCuentaAndTipo(Long nroCuenta, String tipo);
    Movement save(Movement movement);
}
