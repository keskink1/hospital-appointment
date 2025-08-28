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

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 2, max = 50)
    private String name;

    @NotBlank(message = "Surname cannot be blank")
    @Size(min = 2, max = 50)
    private String surname;

    @NotBlank(message = "Registration number cannot be blank")
    @Pattern(regexp = "\\d{8}", message = "Registration number must be 8 digits")
    private String registrationNumber;

    @NotBlank(message = "Department cannot be blank")
    private String department;

    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    private String phoneNumber;

    @NotBlank(message = "Proficiency cannot be blank")
    private String proficiency;
}
