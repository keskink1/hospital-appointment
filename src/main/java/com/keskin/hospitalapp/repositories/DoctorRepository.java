package com.keskin.hospitalapp.repositories;

import com.keskin.hospitalapp.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    List<Doctor> findByDepartment(String department);

    Optional<Doctor> findByRegistrationNumber(String registrationNumber);

    Optional<Doctor> findByPhoneNumber(String phoneNumber);

    Optional<Doctor> findByEmail(String email);

    List<Doctor> findByProficiency(String proficiency);
}