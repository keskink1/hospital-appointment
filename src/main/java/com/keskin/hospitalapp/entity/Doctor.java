package com.keskin.hospitalapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Doctor extends BaseEntity{
    @Column(name = "registration_number", length = 8, unique = true, nullable = false)
    private String registrationNumber;

    @Column(name = "department", nullable = false)
    private String department;

    @Column(name = "phone_number", length = 10, unique = true, nullable = false)
    private String phoneNumber;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "proficiency")
    private String proficiency;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @ManyToMany
    @JoinTable(
            name = "patient_doctor",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "patient_id")
    )
    private Set<Patient> patients = new HashSet<>();

}
