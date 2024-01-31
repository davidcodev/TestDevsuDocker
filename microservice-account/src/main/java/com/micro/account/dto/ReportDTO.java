package com.micro.account.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Objeto que se utiliza para presentar los datos del reporte
 */
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class ReportDTO {
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "UTC")
    private LocalDateTime fecha;
    private String cliente;
    private Long nroCuenta;
    private String tipoCuenta;
    private BigDecimal saldoInicial;
    private Boolean estado;
    private String tipoMovimiento;
    private BigDecimal valor;
    private BigDecimal saldoDisponible;
}
