package com.keskin.hospitalapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Patient extends BaseEntity{

    @Column(name = "national_id", length = 11, unique = true, nullable = false)
    private String nationalId;

    @Column(name = "phone_number", unique = true, length = 10)
    private String phoneNumber;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    // Soft delete flag
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @ManyToMany(mappedBy = "patients")
    private Set<Doctor> doctors = new HashSet<>();
}
