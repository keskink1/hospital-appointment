package com.keskin.hospitalapp.dtos.requests.patient;

import com.keskin.hospitalapp.dtos.requests.user.CreateUserRequestDto;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CreatePatientRequestDto extends CreateUserRequestDto {

    @NotBlank(message = "National ID cannot be blank")
    @Pattern(regexp = "\\d{11}", message = "National ID must be exactly 11 digits")
    private String nationalId;


    @NotNull(message = "Birth date cannot be null")
    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;
}
