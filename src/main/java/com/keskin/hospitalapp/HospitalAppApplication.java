package com.keskin.hospitalapp;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl")
@OpenAPIDefinition(
        info = @Info(
                title = "Hospital appointment application with REST API Documentation",
                description = "This documentation provides comprehensive details about the REST APIs developed for managing hospital appointments and patient monitoring. Each service is designed with a specific responsibility and communicates through RESTful endpoints.",
                version = "v1",
                contact = @Contact(
                        name = "Kaan Keskin",
                        email = "kaankeskin1@hotmail.com"
                )))
public class HospitalAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(HospitalAppApplication.class, args);
    }

}
