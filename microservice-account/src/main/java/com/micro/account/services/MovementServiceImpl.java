package com.micro.account.services;

import com.micro.account.entities.Movement;
import com.micro.account.repositories.MovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MovementServiceImpl implements MovementService {
    @Autowired
    private MovementRepository movementRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Movement> findAll() {
        return (List<Movement>) movementRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Movement> findByNroCuenta(Long nroCuenta) {
        return movementRepository.findByNroCuenta(nroCuenta);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Movement> findByNroCuentaAndTipo(Long nroCuenta, String tipo) {
        return movementRepository.findByNroCuentaAndTipoCuenta(nroCuenta, tipo);
    }

    @Override
    @Transactional
    public Movement save(Movement movement) {
        return movementRepository.save(movement);
    }
}
