package com.micro.account.helper;

import com.micro.account.entities.Movement;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

public class Balance {
    /**
     * Obtiene el saldo actual de la cuenta
     * @param movements Lista de movimientos filtrado por nroCuenta y Tipo
     * @return Saldo actual
     */
    public static BigDecimal getCurrentBalance(List<Movement> movements) {
        return movements
                .stream()
                .max(Comparator.comparing(Movement::getId))
                .map(Movement::getSaldo)
                .orElse(BigDecimal.ZERO);
    }
}
