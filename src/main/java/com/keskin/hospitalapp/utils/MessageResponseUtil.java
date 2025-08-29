package com.keskin.hospitalapp.utils;

import com.keskin.hospitalapp.dtos.responses.ApiResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Locale;


@Component
@AllArgsConstructor
public final class MessageResponseUtil {

    private MessageSource messageSource;

    public <T> ResponseEntity<ApiResponseDto<T>> createResponse(HttpStatus status, String messageKey, T data, Locale locale) {
        String message = messageSource.getMessage(messageKey, null, locale);
        return ResponseEntity.status(status)
                .body(new ApiResponseDto<>(status.value(), message, data));
    }
}
