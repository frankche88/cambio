package com.tutiocambia.cambio.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;


import org.springframework.stereotype.Component;

import com.tutiocambia.cambio.exceptions.CambioNotFoundException;
import com.tutiocambia.cambio.model.Cambio;
import com.tutiocambia.cambio.repository.CambioRepository;
import com.tutiocambia.cambio.service.TipoCambioService;

@Component
public class TipoCambioServiceImpl implements TipoCambioService {

    private CambioRepository cambioRepositorio;

    TipoCambioServiceImpl(CambioRepository repository){
        this.cambioRepositorio = repository;
    }

    @Override
    public BigDecimal calcularTipoCambio(BigDecimal monto, String monedaOrigen, String monedaDestino)
            throws CambioNotFoundException {

        Cambio cambio = obtenerTipoCambio(monedaOrigen, monedaDestino);
        return cambio.calcular(monto);
    }


    
    public Cambio obtenerTipoCambio(String monedaOrigen, String monedaDestino)
            throws CambioNotFoundException {

        if (monedaDestino.equals(monedaOrigen)) {
            // Si la moneda del contribuyente y del comprobante son iguales entonces el
            // factor del tipo de cambio es UNO
            return new Cambio(monedaDestino, BigDecimal.ONE);
        }

        //PEN -> USD
        if ("PEN".equals(monedaOrigen)) {
            return buildResultadoMonedaOrigenPEN(monedaDestino);

        }

        //ME -> PEN
        if ("PEN".equals(monedaDestino)) {
            return buildResultadoMonedaDestinoPEN(monedaOrigen, monedaDestino);

        }

        //ME -> USD
        return buildResultadoMonedaOrigenDestinoNoPEN(monedaOrigen, monedaDestino);
    }

    /**
     * Si las monedas destino y origen no son PEN entoces se necesita conocer el tipo de cambio de ambos con respecto a PEN
     * @param monedaOrigen
     * @param monedaDestino
     * @return
     * @throws CambioNotFoundException
     */
    private Cambio buildResultadoMonedaOrigenDestinoNoPEN(String monedaOrigen,
            String monedaDestino) throws CambioNotFoundException {
        
        Cambio cambioMonedaOrigenRespectoPEN = cambioRepositorio.findOneByMoneda(monedaOrigen);

        if (cambioMonedaOrigenRespectoPEN == null) {
            throw new CambioNotFoundException(monedaOrigen);
        }

        Cambio cambioMonedaDestinoRespectoPEN = cambioRepositorio.findOneByMoneda(monedaDestino);

        if (cambioMonedaDestinoRespectoPEN == null) {
            throw new CambioNotFoundException(monedaDestino);
        }
        
        BigDecimal dividendo = cambioMonedaOrigenRespectoPEN.getTipoCambio();
        
        if (BigDecimal.ZERO.compareTo(dividendo) == 0) {
            throw new CambioNotFoundException(monedaOrigen);
        }
        
        
        BigDecimal divisor = cambioMonedaDestinoRespectoPEN.getTipoCambio();

        if (BigDecimal.ZERO.compareTo(divisor) == 0) {
            throw new CambioNotFoundException(monedaDestino);
        }
        
        
        return new Cambio(monedaDestino, dividendo.divide(divisor, 3, RoundingMode.HALF_UP));
        
    }

    /**
     * Si la moneda de destino es PEN entonces el calculo de tipo de cambio es directo
     * @param monedaOrigen
     * @param monedaDestino
     * @return
     * @throws CambioNotFoundException
     */    
    private Cambio buildResultadoMonedaDestinoPEN(String monedaOrigen, String monedaDestino)
            throws CambioNotFoundException {
        Cambio cambioMonedaOrigenRespectoPEN = cambioRepositorio.findOneByMoneda(monedaOrigen);

        if (cambioMonedaOrigenRespectoPEN == null) {

            throw new CambioNotFoundException(monedaOrigen);

        }

        BigDecimal factorCambio = cambioMonedaOrigenRespectoPEN.getTipoCambio();

        return new Cambio(monedaDestino, factorCambio);
    }

    /**
     * Si la moneda de origen es PEN soles entones se divide entre uno el tipo de cambio de la moneda destino.
     * @param monedaDestino
     * @return
     * @throws CambioNotFoundException
     */
    private Cambio buildResultadoMonedaOrigenPEN(String monedaDestino)
            throws CambioNotFoundException {
        Cambio cambioMonedaDestinoRespectoPEN = cambioRepositorio.findOneByMoneda(monedaDestino);

        if (cambioMonedaDestinoRespectoPEN == null) {
            throw new CambioNotFoundException(monedaDestino);
        }


        BigDecimal divisor = cambioMonedaDestinoRespectoPEN.getTipoCambio();
    
        return new Cambio(monedaDestino, BigDecimal.ONE.divide(divisor, 3, RoundingMode.HALF_UP));
    }
    
}
