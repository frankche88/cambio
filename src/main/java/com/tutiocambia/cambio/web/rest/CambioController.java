package com.tutiocambia.cambio.web.rest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tutiocambia.cambio.exceptions.CambioNotFoundException;
import com.tutiocambia.cambio.model.Cambio;
import com.tutiocambia.cambio.repository.CambioRepository;
import com.tutiocambia.cambio.web.dto.CambioDto;

@RestController
public class CambioController {

    private final CambioRepository repository;

    CambioController(CambioRepository repository) {
      this.repository = repository;
    }

    @PutMapping("/cambio/{id}")
    Cambio replaceEmployee(@RequestBody CambioDto cambioDto, @PathVariable Long id) {
    
    return repository.findById(id)
      .map(cambio -> {
        cambio.setMoneda(cambioDto.getMoneda());
        cambio.setTipoCambio(cambioDto.getTipoCambio());
        return repository.save(cambio);
      }).orElseThrow(() -> 
        new CambioNotFoundException(id)
      );
  }
    
}
