package com.keskin.hospitalapp.exceptions;

public class NationalIdAlreadyExistsException extends RuntimeException {
    public NationalIdAlreadyExistsException(String message) {
        super(message);
    }
}
