package com.tutiocambia.cambio.web.rest;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tutiocambia.cambio.exceptions.CambioNotFoundException;
import com.tutiocambia.cambio.model.Cambio;
import com.tutiocambia.cambio.repository.CambioRepository;
import com.tutiocambia.cambio.service.TipoCambioService;
import com.tutiocambia.cambio.web.dto.ActualizarCambioDto;
import com.tutiocambia.cambio.web.dto.CambioDto;

@RestController
public class CambioController {
    private final CambioRepository repository;
    private final TipoCambioService service;

    Logger logger = LoggerFactory.getLogger(CambioController.class);

    public CambioController(TipoCambioService service, CambioRepository repository) {
      this.repository = repository;
      this.service = service;
    }

    @PutMapping("/cambio/{id}")
    Cambio actualizaTipoCambio(@RequestBody ActualizarCambioDto cambioDto, @PathVariable Long id) {

      logger.info("Actualizando tipo de cambio moneda: {} monto: {}", cambioDto.getMoneda(), cambioDto.getTipoCambio());
    
        return repository.findById(id)
          .map(cambio -> {
              cambio.setMoneda(cambioDto.getMoneda());
              cambio.setTipoCambio(cambioDto.getTipoCambio());
              return repository.save(cambio);
        }).orElseThrow(() -> 
          new CambioNotFoundException(id)
        );
    }

    @PostMapping("/cambio/calcular")
    CambioDto calcularTipoCambio(@RequestBody CambioDto cambioDto) {

        logger.info("Calculando tipo cambio, moneda origen: {} moneda destino: {}  monto: {}", 
          cambioDto.getMonedaOrigen(), cambioDto.getMonedaDestino(), cambioDto.getMonto());
        BigDecimal tipoCambio = service.calcularTipoCambio(cambioDto.getMonto(), cambioDto.getMonedaOrigen(), cambioDto.getMonedaDestino());
        cambioDto.setTipoCambio(tipoCambio);
        return cambioDto;
    }
}
