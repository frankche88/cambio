package com.tutiocambia.cambio.configuracion;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tutiocambia.cambio.model.Cambio;
import com.tutiocambia.cambio.repository.CambioRepository;

@Configuration
public class CargaInicialBaseDatos {

    private static final Logger log = LoggerFactory.getLogger(CargaInicialBaseDatos.class);

    @Bean
    CommandLineRunner initDatabase(CambioRepository repository) {
        return args -> {
            log.info("Carga inicial {}", repository.save(new Cambio("USD", BigDecimal.valueOf(3.87) )));
            log.info("Carga inicial {}", repository.save(new Cambio("EU", BigDecimal.valueOf(4.00))));
        };
    }
    
}
