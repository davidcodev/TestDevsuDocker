package com.micro.account.repositories;

import com.micro.account.entities.Movement;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MovementRepository extends CrudRepository<Movement, Long> {
    List<Movement> findByNroCuenta(Long nroCuenta);
    List<Movement> findByNroCuentaAndTipoCuenta(Long nroCuenta, String tipo);
}
