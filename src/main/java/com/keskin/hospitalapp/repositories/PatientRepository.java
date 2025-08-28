package com.keskin.hospitalapp.repositories;

import com.keskin.hospitalapp.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {


    Optional<Patient> findByNationalId(String nationalId);

    Optional<Patient> findByPhoneNumber(String phoneNumber);
}