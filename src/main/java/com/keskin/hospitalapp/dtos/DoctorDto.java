package com.keskin.hospitalapp.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDto {
    @JsonIgnore
    private Long id;

    private String name;
    private String surname;
    private String registrationNumber;
    private String department;
    private String phoneNumber;
    private String email;
    private String proficiency;
    private Set<PatientDto> patients;

}
