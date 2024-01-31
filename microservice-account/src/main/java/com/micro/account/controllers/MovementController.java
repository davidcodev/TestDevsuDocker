package com.micro.account.controllers;

import com.micro.account.dto.ReportDTO;
import com.micro.account.dto.UpdateAccountDTO;
import com.micro.account.dto.movementDTO.DepositoDTO;
import com.micro.account.entities.Account;
import com.micro.account.entities.AccountPK;
import com.micro.account.entities.Movement;
import com.micro.account.helper.Balance;
import com.micro.account.models.ErrorModel;
import com.micro.account.services.AccountService;
import com.micro.account.services.MovementService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/movimientos")
public class MovementController {
    @Autowired
    private MovementService movementService;

    @Autowired
    private AccountService accountService;

    /**
     * Endpont para listar todos los movimientos realizados sobre todas las cuentas
     * @return Lista de tipo Movement
     */
    @GetMapping("/listar")
    @ResponseStatus(HttpStatus.OK)//Por defecto devolverá un status 200 (OK)
    public List<Movement> listar() {
        return movementService.findAll();
    }

    /**
     * Endpoint para listar todos los movimientos de una cuenta específica
     * @param nroCuenta Núemero de cuenta a listar
     * @return Lista de tipo Movement
     */
    @GetMapping("/listar/{nroCuenta}")
    @ResponseStatus(HttpStatus.OK)
    public List<Movement> listarPorCuenta(@PathVariable Long nroCuenta) {
        return movementService.findByNroCuenta(nroCuenta);
    }

    /**
     * Endpoint para realizar depósitos a una cuenta específica
     * @param deposit Objeto con los campos del depósito
     * @param result Objeto que valida la trama
     * @return Objeto de tipo Movement guardado
     */
    @PostMapping("/deposito")
    public ResponseEntity<?> deposit(@Valid @RequestBody DepositoDTO deposit, BindingResult result) {
        if(result.hasFieldErrors())
            return validation(result);
        //Valida que la cuenta exista
        Account account = accountService.findByIdNroCuentaAndIdTipoCuenta(deposit.getNroCuenta(), deposit.getTipoCuenta());
        if(account == null){
            ErrorModel error = new ErrorModel(
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND,
                    "No se ha encontrado la cuenta del cliente.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }

        //Valida que el estado de la cuenta sea activa (true)
        if(!account.getEstado()){
            ErrorModel error = new ErrorModel(
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND,
                    "El estado actual de la cuenta es inactiva (false) debe activar la cuenta para realizar transacciones.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }

        //Revisa los movimientos sobre la cuenta para tomar el saldo disponible
        List<Movement> movements = movementService.findByNroCuentaAndTipo(deposit.getNroCuenta(), deposit.getTipoCuenta());
        BigDecimal balance = Balance.getCurrentBalance(movements);

        //Toma el saldo inicial de la cuenta y lo agrega al depósito
        balance = balance.add(account.getSaldoInicial());

        //Actualiza el saldo inicial de la cuenta en Movement
        AccountPK pK = new AccountPK(account.getId().getClientId(), account.getId().getNroCuenta(), account.getId().getTipoCuenta());
        UpdateAccountDTO updAccDTO = new UpdateAccountDTO(account.getId().getClientId(), account.getId().getNroCuenta(), account.getId().getTipoCuenta(), BigDecimal.ZERO, account.getEstado());
        accountService.update(pK, updAccDTO);

        Movement movement = new Movement();
        movement.setNroCuenta(deposit.getNroCuenta());
        movement.setTipoCuenta(deposit.getTipoCuenta());
        movement.setFecha(deposit.getFecha());
        movement.setTipoMovimiento("DEPOSITO");
        movement.setValor(deposit.getValor());
        movement.setSaldo(deposit.getValor().add(balance));
        return ResponseEntity.status(HttpStatus.CREATED).body(movementService.save(movement));
    }

    /**
     * Endpoint para realizar retiros de dinero sobre una cuenta específica
     * @param withdraw Objeto con los campos del retiro
     * @return Objeto de tipo Movement guardado
     */
    @PostMapping("/retiro")
    public ResponseEntity<?> withdraw(@Valid @RequestBody DepositoDTO withdraw, BindingResult result) {
        if(result.hasFieldErrors())
            return validation(result);
        //Valida que la cuenta exista
        Account account = accountService.findByIdNroCuentaAndIdTipoCuenta(withdraw.getNroCuenta(), withdraw.getTipoCuenta());
        if(account == null){
            ErrorModel error = new ErrorModel(
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND,
                    "No se ha encontrado la cuenta del cliente.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }

        //Valida que el estado de la cuenta sea activa (true)
        if(!account.getEstado()){
            ErrorModel error = new ErrorModel(
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND,
                    "El estado actual de la cuenta es inactiva (false) debe activar la cuenta para realizar transacciones.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }

        List<Movement> movements = movementService.findByNroCuentaAndTipo(withdraw.getNroCuenta(), withdraw.getTipoCuenta());
        BigDecimal balance = Balance.getCurrentBalance(movements);
        if(balance.compareTo(BigDecimal.ZERO) == 0 || balance.compareTo(withdraw.getValor()) < 0){
            ErrorModel error = new ErrorModel(
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND,
                    "Saldo no disponible.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
        Movement movement = new Movement();
        movement.setNroCuenta(withdraw.getNroCuenta());
        movement.setTipoCuenta(withdraw.getTipoCuenta());
        movement.setFecha(withdraw.getFecha());
        movement.setTipoMovimiento("RETIRO");
        movement.setValor(withdraw.getValor());
        movement.setSaldo(balance.subtract(withdraw.getValor()));
        return ResponseEntity.status(HttpStatus.CREATED).body(movementService.save(movement));
    }

    /**
     * El siguiente método valida los campos de una trama
     * @param result Objeto validado
     * @return Mensaje de error con el estatus Bad_Request
     */
    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(e -> {
            errors.put(e.getField(), "El campo " + e.getField() + " " + e.getDefaultMessage());
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
