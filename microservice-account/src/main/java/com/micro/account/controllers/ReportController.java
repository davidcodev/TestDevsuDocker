package com.micro.account.controllers;

import com.micro.account.dto.ReportDTO;
import com.micro.account.entities.Account;
import com.micro.account.entities.Movement;
import com.micro.account.services.AccountService;
import com.micro.account.services.MovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    /**
     * Endpoint que presenta el reporte de movimientos por fechas y por cliente
     * @param fDesde Fecha de inicio de los movimientos
     * @param fHasta Fecha de corte de los movimientos
     * @param id Identificador del cliente
     * @return Lista de movimientos del cliente filtrada por fecha
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)//Por defecto devolverá un status 200 (OK)
    public List<ReportDTO> reporte(@RequestParam(required = true) LocalDateTime fDesde,
                                   @RequestParam(required = true) LocalDateTime fHasta,
                                   @RequestParam(required = true) Long id) {
        //Datos de la cuenta del cliente:
        List<Account> accounts = accountService.findAllByIds(id);
        List<List<Movement>> listGeneral = new ArrayList<>();
        accounts.forEach(ac -> {
            listGeneral.add(movementService.findByNroCuentaAndTipo(ac.getId().getNroCuenta(), ac.getId().getTipoCuenta()));
        });
        //Filtro la información por el rango de fechas
        List<Movement> filteredMovements = listGeneral.stream()
                .flatMap(List::stream)
                .filter(m -> m.getFecha().isAfter(fDesde) && m.getFecha().isBefore(fHasta))
                .toList();

        List<ReportDTO> reportMovements = new ArrayList<>();

        for (Movement movement : filteredMovements) {
            // Encuentra la cuenta correspondiente para este movimiento
            Account matchingAccount = accounts.stream()
                    .filter(ac -> ac.getId().getNroCuenta().equals(movement.getNroCuenta())
                            && ac.getId().getTipoCuenta().equals(movement.getTipoCuenta()))
                    .findFirst()
                    .orElse(null);

            // Si se encuentra una cuenta correspondiente, crea el DTO
            if (matchingAccount != null) {
                ReportDTO reportDTO = new ReportDTO();
                reportDTO.setFecha(movement.getFecha());
                reportDTO.setCliente(accountService.findCustomerById(matchingAccount.getId().getClientId()));
                reportDTO.setNroCuenta(matchingAccount.getId().getNroCuenta());
                reportDTO.setTipoCuenta(matchingAccount.getId().getTipoCuenta());
                reportDTO.setSaldoInicial(matchingAccount.getSaldoInicial());
                reportDTO.setEstado(matchingAccount.getEstado());
                reportDTO.setTipoMovimiento(movement.getTipoMovimiento());
                reportDTO.setValor(movement.getValor());
                reportDTO.setSaldoDisponible(movement.getSaldo());
                reportMovements.add(reportDTO);
            }
        }
        return reportMovements;
    }
}
