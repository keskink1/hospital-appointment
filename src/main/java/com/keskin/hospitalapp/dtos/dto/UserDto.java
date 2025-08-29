package com.keskin.hospitalapp.dtos.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.keskin.hospitalapp.entities.Role;
import lombok.Data;

@Data
public class UserDto {
    @JsonIgnore
    private Long id;

    private String email;

    private String phoneNumber;

    private String name;

    private String surname;

    private Role role;
}
