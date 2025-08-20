package com.keskin.hospitalapp.exceptions;

public class RegistrationNumberAlreadyExists extends RuntimeException {
    public RegistrationNumberAlreadyExists(String message) {
        super(message);
    }
}
