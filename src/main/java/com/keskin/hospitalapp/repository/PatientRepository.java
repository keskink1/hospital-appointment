package com.keskin.hospitalapp.repository;

import com.keskin.hospitalapp.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    //active patients
    List<Patient> findByIsDeletedFalse();

    Optional<Patient> findByIdAndIsDeletedFalse(Long id);

    Optional<Patient> findByNationalIdAndIsDeletedFalse(String nationalId);

    Optional<Patient> findByPhoneNumberAndIsDeletedFalse(String phoneNumber);
}