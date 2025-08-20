package com.keskin.hospitalapp.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseDto<T> {
    private int status;
    private String message;
    private T data;
}
