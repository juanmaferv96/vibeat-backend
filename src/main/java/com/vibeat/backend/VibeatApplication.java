package com.vibeat.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.vibeat.backend")  // Escanear el paquete de las entidades
public class VibeatApplication {

    public static void main(String[] args) {
        SpringApplication.run(VibeatApplication.class, args);
    }
}
