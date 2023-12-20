package com.tutiocambia.cambio.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.tutiocambia.cambio.exceptions.CambioNotFoundException;
import com.tutiocambia.cambio.model.Cambio;
import com.tutiocambia.cambio.repository.CambioRepository;
import com.tutiocambia.cambio.service.TipoCambioService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TipoCambioServiceImplTest {

    @Mock
    private CambioRepository repository;

    @InjectMocks
    private TipoCambioService tipoCambioService = new TipoCambioServiceImpl(repository);

    private BigDecimal monto = BigDecimal.valueOf(7.0d);

    // tipo de cambio Para convertir dólares a soles
    private BigDecimal tipoCambioDolar = BigDecimal.valueOf(3.7d);

    private BigDecimal tipoCambioEuro = BigDecimal.valueOf(4.2d);

    
    @Test
    void calcularCambio_moneda_origen_soles_moneda_destino_soles() throws CambioNotFoundException {

        /// ambas monedas son iguales

        // moneda origen = PEN
        // moneda destino = PEN

        // factor de cambio = 1
        // moneda origen = PEN
        // moneda destino = PEN

        String monedaOrigen = "PEN";

        String monedaDestino = "PEN";

        BigDecimal nuevoTipoCambio = tipoCambioService.calcularTipoCambio(monto,
                monedaOrigen, monedaDestino);

        assertTrue(monto.compareTo(nuevoTipoCambio) == 0);

    }

    @Test
    void calcularCambio_moneda_origen_dolares_moneda_destino_dolares()
            throws CambioNotFoundException {

        // ambas monedas son iguales

        // moneda origen = USD
        // moneda destino = USD

        // factor de cambio = 1
        // moneda origen = USD
        // moneda destino = USD

        String monedaOrigen = "USD";

        String monedaDestino = "USD";

        BigDecimal nuevoTipoCambio = tipoCambioService.calcularTipoCambio(monto, monedaOrigen, monedaDestino);


        // Si los tipos de cambio de origen y destino son iguales el tipo de
        // el factor de cambio es 1
        assertTrue(monto.compareTo(nuevoTipoCambio) == 0);

    }

    @Test
    void calcularCambio_moneda_origen_soles_moneda_destino_dolares()
            throws CambioNotFoundException {

        // Moneda origen soles y destino dolares

        // moneda origen = PEN
        // moneda destino = USD

        // factor de cambio definido para dolares = 1 / 3.7
        // moneda origen = USD
        // moneda destino = USD

        String monedaOrigen = "PEN"; // moneda del CP

        String monedaDestino = "USD"; // conta

        when(repository.findOneByMoneda(monedaDestino))
                .thenReturn(new Cambio(monedaDestino, tipoCambioDolar));

        BigDecimal nuevoTipoCambio = tipoCambioService.calcularTipoCambio(monto, monedaOrigen, monedaDestino);

        // Realizar el calculo
        BigDecimal montoEsperado = BigDecimal.valueOf(1.0d).divide(tipoCambioDolar, 3, RoundingMode.HALF_UP).multiply(monto);
        assertTrue(montoEsperado.compareTo(nuevoTipoCambio) == 0);


    }

    @Test
    void calcularCambio_moneda_origen_dolares_moneda_destino_soles()
            throws CambioNotFoundException {

        // Moneda origen <> soles> y destino soles

        // moneda origen = USD
        // moneda destino = PEN

        // factor de cambio definido para dolares = 3.7
        // moneda origen = USD
        // moneda destino = PEN

        String monedaOrigen = "USD";

        String monedaDestino = "PEN";

        when(repository.findOneByMoneda(monedaOrigen))
                .thenReturn(new Cambio(monedaOrigen, tipoCambioDolar));

        BigDecimal nuevoTipoCambio = tipoCambioService.calcularTipoCambio(monto, monedaOrigen, monedaDestino);


        // Realizar el calculo
        BigDecimal montoEsperado = tipoCambioDolar.multiply(monto);

        assertTrue(montoEsperado.compareTo(nuevoTipoCambio) == 0);


    }

    @Test
    void calcularCambio_moneda_origen_euros_moneda_destino_dolares()
            throws CambioNotFoundException {

        // Moneda origen y moneda destino <> soles

        // moneda origen (origen) = EUR 4.2
        // moneda destino (destino) = USD 3.7

        // resultado
        // tipo de el factor de cambio es moneda origen/ destino = 4.2 / 3.7
        // moneda origen = EUR
        // moneda destino = USD

        String monedaOrigen = "EUR";

        String monedaDestino = "USD";

        when(repository.findOneByMoneda(monedaOrigen))
                .thenReturn(new Cambio(monedaOrigen, tipoCambioEuro));

        when(repository.findOneByMoneda(monedaDestino))
                .thenReturn(new Cambio(monedaDestino, tipoCambioDolar));

        BigDecimal nuevoTipoCambio = tipoCambioService.calcularTipoCambio(monto, monedaOrigen, monedaDestino);

        // Si el tipo de cambio origen y el destino son diferentes de PEN
        // devolver la división del precio del destino entre el origen
        BigDecimal montoEsperado = tipoCambioEuro.divide(tipoCambioDolar, 3, RoundingMode.HALF_UP).multiply(monto);
        assertTrue(montoEsperado.compareTo(nuevoTipoCambio) == 0);


    }

    @Test
    void calcularCambio_moneda_origen_dolares_moneda_destino_euros()
            throws CambioNotFoundException {

        // Moneda origen y moneda destino <> soles

        // moneda origen (origen) = USD
        // moneda destino (destino) = EUR

        // resultado
        // tipo de el factor de cambio es moneda origen / destino = 3.7 / 4.2
        // moneda origen = USD
        // moneda destino = EUR

        // realizar la prueba
        String monedaOrigen = "USD";

        String monedaDestino = "EUR";

        
        when(repository.findOneByMoneda(monedaOrigen))
                .thenReturn(new Cambio(monedaOrigen, tipoCambioDolar));

        when(repository.findOneByMoneda(monedaDestino))
                .thenReturn(new Cambio(monedaDestino, tipoCambioEuro));

        BigDecimal nuevoTipoCambio = tipoCambioService.calcularTipoCambio(monto, monedaOrigen, monedaDestino);

        // Si el tipo de cambio origen y el destino son diferentes de PEN
        // devolver la división del cambio moneda destino entre el origen
        BigDecimal montoEsperado = tipoCambioDolar.divide(tipoCambioEuro, 3,
                RoundingMode.HALF_UP).multiply(monto);
        assertTrue(montoEsperado.compareTo(nuevoTipoCambio) == 0);

    }

    @Test()
    void calcularCambio_moneda_origen_dolares_moneda_destino_yen_tipo_cambio_euro_noencontrado() {

        // Moneda origen y moneda destino <> soles y euro no

        // moneda origen = USD
        // moneda destino = EUR

        // resultado
        // Error Euro no encontrado

        // realizar la prueba -> debería arrojar un mensaje de excepción
        String monedaOrigen = "USD";

        String monedaDestino = "JPY";

        
        when(repository.findOneByMoneda(monedaOrigen))
                .thenReturn(new Cambio(monedaOrigen, tipoCambioDolar));

        CambioNotFoundException exception = assertThrows(CambioNotFoundException.class, () -> {
            tipoCambioService.calcularTipoCambio(monto, monedaOrigen, monedaDestino);
        });

        String mensajeEsperado = "No existe tipo de cambio para la moneda: ";
        String mensajeActual = exception.getMessage();

        assertTrue(mensajeActual.contains(mensajeEsperado));
        
    }
    
}
