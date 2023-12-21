package com.tutiocambia.cambio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tutiocambia.cambio.model.Cambio;

public interface CambioRepository extends JpaRepository<Cambio, String> {
    public Cambio findOneByMoneda(String moneda);
}
