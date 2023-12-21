package com.tutiocambia.cambio.web.dto;

import java.math.BigDecimal;
import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CambioDto {
    private BigDecimal tipoCambio;
    
    @NotNull(message = "monto: Campo requerido")
    private BigDecimal monto;
    
    @NotBlank(message = "monedaOrigen: Campo requerido")
    private String monedaOrigen;
    
    @NotBlank(message = "monedaDestino: Campo requerido")
    private String monedaDestino;


    public CambioDto() {

    }


    public CambioDto(String monedaOrigen, BigDecimal tipoCambioDto) {
        this.monedaOrigen = monedaOrigen;
        this.tipoCambio = tipoCambioDto;
    }

    public BigDecimal getTipoCambio() {
        return this.tipoCambio;
    }

    public void setTipoCambio(BigDecimal tipoCambio) {
        this.tipoCambio = tipoCambio;
    }

    public String getMoneda() {
        return this.monedaOrigen;
    }

    public void setMoneda(String moneda) {
        this.monedaOrigen = moneda;
    }


    public BigDecimal getMonto() {
        return this.monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getMonedaOrigen() {
        return this.monedaOrigen;
    }

    public void setMonedaOrigen(String monedaOrigen) {
        this.monedaOrigen = monedaOrigen;
    }

    public String getMonedaDestino() {
        return this.monedaDestino;
    }

    public void setMonedaDestino(String monedaDestino) {
        this.monedaDestino = monedaDestino;
    }


    @Override
    public boolean equals(Object o) {
  
        if (this == o)
            return true;
        if (!(o instanceof ActualizarCambioDto))
            return false;

        CambioDto cambioDto = (CambioDto) o;
        return Objects.equals(this.monedaOrigen, cambioDto.monedaOrigen)
            && Objects.equals(this.tipoCambio, cambioDto.tipoCambio);
    }
  
    @Override
    public int hashCode() {
        return Objects.hash(this.monedaOrigen, this.tipoCambio);
    }
  
    @Override
    public String toString() {
        return String.format("[%s,%s]", this.monedaOrigen, this.tipoCambio);
    }
}
