package com.keskin.hospitalapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE patient SET is_deleted = true WHERE id = ?")
@SQLRestriction("is_deleted = false")
public class Patient extends BaseEntity{

    @Column(name = "national_id", length = 11, unique = true, nullable = false)
    private String nationalId;

    @Column(name = "phone_number", unique = true, length = 10)
    private String phoneNumber;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @ManyToMany(mappedBy = "patients")
    private Set<Doctor> doctors = new HashSet<>();

    public void softDelete() {
        if (!doctors.isEmpty()) {
            throw new IllegalStateException("Cannot delete patient with assigned doctors");
        }
        this.isDeleted = true;
    }
}
