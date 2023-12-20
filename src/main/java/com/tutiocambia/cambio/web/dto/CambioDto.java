package com.tutiocambia.cambio.web.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class CambioDto {
    private BigDecimal tipoCambio; 
    private String moneda;


    public CambioDto() {

    }


    public CambioDto(String moneda, BigDecimal tipoCambioDto) {
        this.moneda = moneda;
        this.tipoCambio = tipoCambioDto;
    }

    public BigDecimal getTipoCambio() {
        return this.tipoCambio;
    }

    public void setTipoCambio(BigDecimal tipoCambio) {
        this.tipoCambio = tipoCambio;
    }

    public String getMoneda() {
        return this.moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    @Override
    public boolean equals(Object o) {
  
        if (this == o)
            return true;
        if (!(o instanceof CambioDto))
            return false;

        CambioDto cambioDto = (CambioDto) o;
        return Objects.equals(this.moneda, cambioDto.moneda)
            && Objects.equals(this.tipoCambio, cambioDto.tipoCambio);
    }
  
    @Override
    public int hashCode() {
        return Objects.hash(this.moneda, this.tipoCambio);
    }
  
    @Override
    public String toString() {
        return String.format("[%s,%s]", this.moneda, this.tipoCambio);
    }
}
