package com.keskin.hospitalapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ResourceAlreadyExistsException extends RuntimeException {

    public ResourceAlreadyExistsException(String fieldName, String fieldValue) {
        super(String.format("Resource with %s '%s' already exists!", fieldName, fieldValue));
    }
}