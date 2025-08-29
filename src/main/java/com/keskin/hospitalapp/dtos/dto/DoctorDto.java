package com.keskin.hospitalapp.dtos.dto;

import lombok.*;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDto extends UserDto {

    private String registrationNumber;
    private String department;
    private String proficiency;
}
