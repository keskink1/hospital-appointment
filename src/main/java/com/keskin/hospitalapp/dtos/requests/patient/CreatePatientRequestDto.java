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

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 2, max = 50)
    private String name;

    @NotBlank(message = "Surname cannot be blank")
    @Size(min = 2, max = 50)
    private String surname;

    @NotBlank(message = "National ID cannot be blank")
    @Pattern(regexp = "\\d{11}", message = "National ID must be exactly 11 digits")
    private String nationalId;

    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(regexp = "\\d{10}", message = "Phone number must be exactly 10 digits")
    private String phoneNumber;

    @NotNull(message = "Birth date cannot be null")
    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;
}
