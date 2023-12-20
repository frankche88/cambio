package com.tutiocambia.cambio.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Cambio {

    private @Id @GeneratedValue Long id;
    private BigDecimal tipoCambio; 
    private String moneda;
    private Date fechaModificcion;


    public Cambio() {

    }


    public Cambio(String moneda, BigDecimal tipoCambio) {
        this.moneda = moneda;
        this.tipoCambio = tipoCambio;
        this.fechaModificcion = new Date();
    }


    public BigDecimal calcular(BigDecimal value) {

        return value.multiply(tipoCambio).setScale(3);
    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
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
        return Objects.equals(this.id, cambio.id) && Objects.equals(this.moneda, cambio.moneda)
            && Objects.equals(this.tipoCambio, cambio.tipoCambio);
    }
  
    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.moneda, this.tipoCambio);
    }
  
    @Override
    public String toString() {
        return String.format("[%d,%s,%s]", this.id, this.moneda, this.tipoCambio);
    }
    
}
