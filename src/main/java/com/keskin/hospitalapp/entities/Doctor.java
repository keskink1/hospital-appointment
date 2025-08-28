package com.keskin.hospitalapp.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE doctors SET is_deleted = true WHERE id = ?")
@SQLRestriction("is_deleted = false")
public class Doctor extends User {

    private static final int MAX_PATIENTS = 30;

    @Column(name = "registration_number", length = 8, unique = true, nullable = false)
    private String registrationNumber;

    @Column(name = "department", nullable = false)
    private String department;

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


    public void softDelete() {
        if (!patients.isEmpty()) {
            throw new IllegalStateException(
                    "Doctor cannot be deleted. Remove associated patients first!"
            );
        }
        this.isDeleted = true;
    }

    public void changePassword(String oldPassword, String newPassword, PasswordEncoder passwordEncoder) {
        if (!passwordEncoder.matches(oldPassword, this.getPassword())) {
            throw new IllegalArgumentException("Old password does not match");
        }
        if (newPassword == null || newPassword.isBlank() || newPassword.length() < 6) {
            throw new IllegalArgumentException("New password is invalid");
        }
        this.setPassword(passwordEncoder.encode(newPassword));
    }


    public void addPatient(Patient patient) {
        if (patients.size() >= MAX_PATIENTS) {
            throw new IllegalStateException("A doctor cannot have more than 30 patients");
        }
        this.patients.add(patient);
    }

    public void removePatient(Patient patient) {
        this.patients.remove(patient);
    }
}
