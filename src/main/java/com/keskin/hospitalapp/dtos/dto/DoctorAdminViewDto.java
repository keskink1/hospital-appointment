package com.keskin.hospitalapp.dtos.dto;

import lombok.*;

import java.util.Set;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DoctorAdminViewDto extends UserDto{

    private String registrationNumber;
    private String department;
    private String proficiency;
    private Set<PatientDto> patients;

}
