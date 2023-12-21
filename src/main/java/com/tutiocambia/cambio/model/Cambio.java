package com.tutiocambia.cambio.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Cambio {

    private @Id String moneda;
    private BigDecimal tipoCambio; 
    private Date fechaModificcion;


    public Cambio() {

    }


    public Cambio(String moneda, BigDecimal tipoCambio) {
        this.moneda = moneda;
        this.tipoCambio = tipoCambio;
        this.fechaModificcion = new Date();
    }


    public BigDecimal calcular(BigDecimal value) {

        return value.multiply(tipoCambio).setScale(3, RoundingMode.HALF_UP);
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

    public Date getFechaModificcion() {
        return this.fechaModificcion;
    }

    public void setFechaModificcion(Date fechaModificcion) {
        this.fechaModificcion = fechaModificcion;
    }

    @Override
    public boolean equals(Object o) {
  
        if (this == o)
            return true;
        if (!(o instanceof Cambio))
            return false;

        Cambio cambio = (Cambio) o;
        return Objects.equals(this.moneda, cambio.moneda)
            && Objects.equals(this.tipoCambio, cambio.tipoCambio);
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
