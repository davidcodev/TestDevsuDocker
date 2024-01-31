package com.micro.account.controllers;

import com.micro.account.dto.*;
import com.micro.account.entities.Account;
import com.micro.account.entities.AccountPK;
import com.micro.account.models.ErrorModel;
import com.micro.account.services.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/cuentas")
public class AccountController {
    /**
     * Inyección de la interfaz AccountService
     */
    @Autowired
    AccountService accountService;

    /**
     * Endpoint que lista TODAS las cuentas de TODOS los clientes registrados
     * @return listado de cuentas
     */
    @GetMapping("/listar")
    @ResponseStatus(HttpStatus.OK)//Por defecto devolverá un status 200 (OK)
    public List<AccountDTO> listar() {
        return accountService.findAll()
                .stream()
                .map(aco -> {
                    AccountDTO acDTO = new AccountDTO();
                    acDTO.setNroCuenta(aco.getId().getNroCuenta());
                    acDTO.setTipo(aco.getId().getTipoCuenta());
                    acDTO.setSaldoInicial(aco.getSaldoInicial());
                    acDTO.setEstado(aco.getEstado());
                    acDTO.setCliente(accountService.findCustomerById(aco.getId().getClientId()));
                    return acDTO;
                }).toList();
    }

    /**
     * Endpoint que lista todas las cuentas de un cliente específico
     * @return listado de cuentas de un cliente
     */
    @GetMapping("/listar/{id}")
    public List<AccountDTO> listarCuenta(@PathVariable Long id) {
        return accountService.findAllByIds(id)
                .stream()
                .map(aco -> {
                    AccountDTO acDTO = new AccountDTO();
                    acDTO.setNroCuenta(aco.getId().getNroCuenta());
                    acDTO.setTipo(aco.getId().getTipoCuenta());
                    acDTO.setSaldoInicial(aco.getSaldoInicial());
                    acDTO.setEstado(aco.getEstado());
                    acDTO.setCliente(accountService.findCustomerById(aco.getId().getClientId()));
                    return acDTO;
                }).toList();
    }

    /**
     * Endpont para crear una cuenta a un cliente registrado
     * @param account Objeto con los datos de la cuenta
     * @return Cuenta creada
     */
    @PostMapping("/crear")
    public ResponseEntity<?> create(@Valid @RequestBody CreateAccountDTO account, BindingResult result) {
        if(result.hasFieldErrors())
            return validation(result);
        //Valida que el cliente exista:
        if(accountService.findCustomerById(account.getIdCliente()).compareTo("") == 0){
            ErrorModel error = new ErrorModel(
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND,
                    "No se ha encontrado el cliente, debe crearlo antes.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }

        //Valida que el estado del cliente sea true (activo)
        CustomerDTO customer = accountService.findAllCustomerById(account.getIdCliente());
        if(!customer.getEstado()){
            ErrorModel error = new ErrorModel(
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND,
                    "El estado del cliente es inactivo (false) debe activarlo.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }

        //Valida que no exista una cuenta con el mismo número y mismo tipo
        Account account1 = accountService.findByIdNroCuentaAndIdTipoCuenta(account.getNroCuenta(), account.getTipo());
        if(account1 != null){
            ErrorModel error = new ErrorModel(
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND,
                    "Ya existe una cuenta con el mismo número asignado a otro cliente.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }

        AccountPK pK = new AccountPK(account.getIdCliente(), account.getNroCuenta(), account.getTipo());
        Account acc = new Account(pK, account.getSaldoInicial(), account.getEstado());

        //Valida la duplicidad de una cuenta
        Optional<Account> accountOpt = accountService.findByPK(pK);
        if(accountOpt.isPresent()){
            ErrorModel error = new ErrorModel(
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND,
                    "Ya existe una cuenta con estos datos.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.save(acc));
    }

    /**
     * Endpoint para actualizar los datos de la cuenta de un cliente
     * @param account Datos de la cuenta
     * @return cuenta actualizada / error
     */
    @PatchMapping("/actualizar")
    public ResponseEntity<?> update(@Valid @RequestBody UpdateAccountDTO account, BindingResult result) {
        if(result.hasFieldErrors())
            return validation(result);
        AccountPK pK = new AccountPK(account.getId(), account.getNroCuenta(), account.getTipo());
        Optional<Account> accountOpt = accountService.update(pK, account);
        if(accountOpt.isPresent())
            return ResponseEntity.status(HttpStatus.OK).body(accountOpt);
        ErrorModel error = new ErrorModel(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND,
                "No es posible actualizar la cuenta del cliente, revisar id, nroCuenta y tipo (PK)");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }


    /**
     * Endpoint para eliminar una cuenta registrada
     * @param id Id del cliente
     * @param account Objeto con los datos de la cuenta a eliminar
     * @return Cuenta eliminada
     */
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, @Valid @RequestBody DeleteAccountDTO account, BindingResult result){
        if(result.hasFieldErrors())
            return validation(result);
        AccountPK pK = new AccountPK(id, account.getNroCuenta(), account.getTipo());
        Optional<Account> accountOpt = accountService.delete(pK);
        if(accountOpt.isPresent()) return ResponseEntity.ok(accountOpt);
        ErrorModel error = new ErrorModel(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND,
                "No es posible eliminar la cuenta, es posible que el id no exista");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
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
