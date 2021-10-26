package com.codigoton;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author Camilo Hurtado
 */
@SpringBootApplication
@EnableJpaRepositories
public class Codigoton {

    public static void main(String[] args) {
        SpringApplication.run(Codigoton.class, args);
    }
}
