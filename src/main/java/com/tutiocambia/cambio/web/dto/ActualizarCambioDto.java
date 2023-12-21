package com.tutiocambia.cambio.web.dto;

import java.math.BigDecimal;
import java.util.Objects;

import javax.validation.constraints.NotNull;

public class ActualizarCambioDto {
    @NotNull(message = "tipoCambio: Campo requerido")
    private BigDecimal tipoCambio;


    public ActualizarCambioDto() {

    }


    public ActualizarCambioDto(BigDecimal tipoCambioDto) {
        this.tipoCambio = tipoCambioDto;
    }

    public BigDecimal getTipoCambio() {
        return this.tipoCambio;
    }

    public void setTipoCambio(BigDecimal tipoCambio) {
        this.tipoCambio = tipoCambio;
    }

    @Override
    public boolean equals(Object o) {
  
        if (this == o)
            return true;
        if (!(o instanceof ActualizarCambioDto))
            return false;

        ActualizarCambioDto cambioDto = (ActualizarCambioDto) o;
        return Objects.equals(this.tipoCambio, cambioDto.tipoCambio);
    }
  
    @Override
    public int hashCode() {
        return Objects.hash(this.tipoCambio);
    }
  
    @Override
    public String toString() {
        return String.format("[%s]", this.tipoCambio);
    }
}
