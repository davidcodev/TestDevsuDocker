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

    /**
     * Busca todos los movimientos de todas la cuentas
     * @return Objeto de tipo List[Movement]
     */
    @Override
    @Transactional(readOnly = true)
    public List<Movement> findAll() {
        return (List<Movement>) movementRepository.findAll();
    }

    /**
     * Busca los movimientos de una cuenta específica
     * @param nroCuenta Número de cuenta del cliente
     * @return Objeto de tipo List[Movement]
     */
    @Override
    @Transactional(readOnly = true)
    public List<Movement> findByNroCuenta(Long nroCuenta) {
        return movementRepository.findByNroCuenta(nroCuenta);
    }

    /**
     * Busca los movimientos de una cuenta a través del NroCuenta y Tipo
     * @param nroCuenta Número de cuenta del cliente
     * @param tipo Tipo de cuenta (ahorros/corriente)
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<Movement> findByNroCuentaAndTipo(Long nroCuenta, String tipo) {
        return movementRepository.findByNroCuentaAndTipoCuenta(nroCuenta, tipo);
    }

    /**
     * Guarda un movimiento (depósito/retiro)
     * @param movement Objeto de tipo Movement con los datos del movimiento
     * @return Objeto de tipo Movement
     */
    @Override
    @Transactional
    public Movement save(Movement movement) {
        return movementRepository.save(movement);
    }
}
