package com.micro.account.dto.movementDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Objeto utilizado para realizar depósitos y retiros
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class DepositoDTO {
    @NotNull(message = "no se ha encontrado")
    private Long nroCuenta;
    @NotEmpty(message = "no se ha encontrado")
    @Pattern(regexp = "^(AHORROS|CORRIENTE)$", message = "solo puede ser AHORROS o CORRIENTE")
    private String tipoCuenta;
    @NotNull(message = "no se ha encontrado")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "UTC")
    private LocalDateTime fecha;
    @NotNull(message = "no se ha encontrado")
    private BigDecimal valor;
}
