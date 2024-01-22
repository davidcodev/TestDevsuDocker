package com.micro.account.controllers;

import com.micro.account.dto.ReportDTO;
import com.micro.account.entities.Account;
import com.micro.account.entities.Movement;
import com.micro.account.services.AccountService;
import com.micro.account.services.MovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/reportes")
public class ReportController {
    @Autowired
    AccountService accountService;

    @Autowired
    MovementService movementService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)//Por defecto devolverá un status 200 (OK)
    public List<ReportDTO> reporte(@RequestParam(required = true) LocalDateTime fDesde,
                                   @RequestParam(required = true) LocalDateTime fHasta,
                                   @RequestParam(required = true) Long id) {
        //Datos de la cuenta del cliente:
        List<Account> accounts = accountService.findAllByIds(id);

        //Datos de los movimientos:
        List<Movement> movements = movementService.findByNroCuenta(accounts.get(0).getId().getNroCuenta());

        Account account = accounts.get(0); // Asumes una sola cuenta por cliente
        Long nroCuenta = account.getId().getNroCuenta();
        String tipoCuenta = account.getId().getTipoCuenta();
        BigDecimal saldoInicial = account.getSaldoInicial();
        Boolean estado = account.getEstado();

        List<Movement> filteredMovements = movements
                .stream()
                .filter(m -> m.getFecha().isAfter(fDesde) && m.getFecha().isBefore(fHasta))
                .toList();

        List<ReportDTO> reportMovements = new ArrayList<>();

        for (Movement movement : filteredMovements) {
            ReportDTO reportDTO = new ReportDTO();
            reportDTO.setFecha(movement.getFecha());
            reportDTO.setCliente(accountService.findCustomerById(account.getId().getClientId()));
            reportDTO.setNroCuenta(nroCuenta);
            reportDTO.setTipoCuenta(tipoCuenta);
            reportDTO.setSaldoInicial(saldoInicial);
            reportDTO.setEstado(estado);
            reportDTO.setTipoMovimiento(movement.getTipoMovimiento());
            reportDTO.setValor(movement.getValor());
            reportDTO.setSaldoDisponible(movement.getSaldo());
            reportMovements.add(reportDTO);
        }
        return reportMovements;
    }
}
