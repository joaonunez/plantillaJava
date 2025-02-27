package com.example.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.HiddenHttpMethodFilter;

@SpringBootApplication
public class ExamenJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExamenJavaApplication.class, args);
    }

    // Habilitar métodos HTTP ocultos (PUT y DELETE en formularios HTML)
    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }
}
