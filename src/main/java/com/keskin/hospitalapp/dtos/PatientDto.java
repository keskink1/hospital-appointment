package com.keskin.hospitalapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDto {
    private Long id;
    private String name;
    private String surname;
    private String nationalId;
    private String phoneNumber;
    private LocalDate birthDate;
}
