package com.keskin.hospitalapp.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public final class StatusController {

    private static final Logger logger = LoggerFactory.getLogger(StatusController.class);


    private StatusController() {
    }

    @GetMapping("/status")
    public ResponseEntity<String> getStatus() {
        logger.info("Received request to check application status.");
        return ResponseEntity.ok("HospitalApp is up and running!");
    }
}
