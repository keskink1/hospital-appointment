package com.keskin.hospitalapp.dtos.dto;

import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PatientDto extends UserDto{

    private String nationalId;
    private LocalDate birthDate;
}
