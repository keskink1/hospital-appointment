package com.keskin.hospitalapp.dtos.requests.doctor;

import com.keskin.hospitalapp.dtos.requests.user.CreateUserRequestDto;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CreateDoctorRequestDto extends CreateUserRequestDto {

    @NotBlank(message = "Registration number cannot be blank")
    @Pattern(regexp = "\\d{8}", message = "Registration number must be 8 digits")
    private String registrationNumber;

    @NotBlank(message = "Department cannot be blank")
    private String department;

    @NotBlank(message = "Proficiency cannot be blank")
    private String proficiency;
}
