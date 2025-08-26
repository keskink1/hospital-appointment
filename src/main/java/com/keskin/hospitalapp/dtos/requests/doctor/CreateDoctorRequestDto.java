package com.keskin.hospitalapp.dtos.requests.doctor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateDoctorRequestDto {
    @NotBlank(message = "Name cannot be empty")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @NotBlank(message = "Surname cannot be empty")
    @Size(min = 2, max = 50, message = "Surname must be between 2 and 50 characters")
    private String surname;

    @NotBlank(message = "Registration number cannot be empty")
    @Pattern(regexp = "^\\d{8}$", message = "Registration number must be 8 characters")
    private String registrationNumber;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 6, max = 50, message = "Password must be between 6 and 50 characters")
    private String password;

    @NotBlank(message = "Department cannot be empty")
    private String department;

    @NotBlank(message = "Phone number cannot be empty")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits")
    private String phoneNumber;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Proficiency cannot be empty")
    private String proficiency;

}
