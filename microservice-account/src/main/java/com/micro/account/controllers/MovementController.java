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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movimientos")
public class MovementController {
    @Autowired
    private MovementService movementService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/listar")
    @ResponseStatus(HttpStatus.OK)//Por defecto devolverá un status 200 (OK)
    public List<Movement> listar() {
        return movementService.findAll();
    }

    @GetMapping("/listar/{nroCuenta}")
    @ResponseStatus(HttpStatus.OK)
    public List<Movement> listarPorCuenta(@PathVariable Long nroCuenta) {
        return movementService.findByNroCuenta(nroCuenta);
    }

    @PostMapping("/deposito")
    public ResponseEntity<?> deposit(@RequestBody DepositoDTO deposit) {
        List<Movement> movements = movementService.findByNroCuentaAndTipo(deposit.getNroCuenta(), deposit.getTipoCuenta());
        BigDecimal balance = Balance.getCurrentBalance(movements);

        //Toma el saldo inicial de la cuenta y lo agrega al depósito
        Account account = accountService.findByIdNroCuentaAndIdTipoCuenta(deposit.getNroCuenta(), deposit.getTipoCuenta());
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

    @PostMapping("/retiro")
    public ResponseEntity<?> withdraw(@RequestBody DepositoDTO withdraw) {
        List<Movement> movements = movementService.findByNroCuentaAndTipo(withdraw.getNroCuenta(), withdraw.getTipoCuenta());
        BigDecimal balance = Balance.getCurrentBalance(movements);
        if(balance.compareTo(BigDecimal.ZERO) == 0 || balance.compareTo(withdraw.getValor()) < 0){
            ErrorModel error = new ErrorModel(
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND,
                    "Saldo no disponible");
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
}
