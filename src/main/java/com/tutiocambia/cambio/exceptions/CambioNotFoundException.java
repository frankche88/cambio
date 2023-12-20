package com.tutiocambia.cambio.exceptions;

public class CambioNotFoundException extends RuntimeException {

    public CambioNotFoundException(Long id) {
        super("No existe tipo de cambio para el id: " + id);
    }
    
    
    public CambioNotFoundException(String moneda) {
        super("No existe tipo de cambio para la moneda: " + moneda);
    }
}
