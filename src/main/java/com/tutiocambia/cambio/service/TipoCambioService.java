package com.tutiocambia.cambio.service;

import java.math.BigDecimal;

import com.tutiocambia.cambio.exceptions.CambioNotFoundException;

public interface TipoCambioService {
    public BigDecimal calcularTipoCambio(BigDecimal monto, String monedaOrigen, String monedaDestino)
            throws CambioNotFoundException;
}
