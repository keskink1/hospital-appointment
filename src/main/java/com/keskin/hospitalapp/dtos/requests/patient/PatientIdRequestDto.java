package com.keskin.hospitalapp.dtos.requests.patient;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PatientIdRequestDto {

    @NotNull(message = "Patient ID cannot be null")
    private Long patientId;
}
