package com.keskin.hospitalapp.repository;

import com.keskin.hospitalapp.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    List<Doctor> findByDepartmentAndIsDeletedFalse(String department);


    List<Doctor> findByIsDeletedFalse();

    Optional<Doctor> findByIdAndIsDeletedFalse(Long id);

    Optional<Doctor> findByRegistrationNumberAndIsDeletedFalse(String registrationNumber);

    Optional<Doctor> findByPhoneNumberAndIsDeletedFalse(String phoneNumber);

    Optional<Doctor> findByEmailAndIsDeletedFalse(String email);

    List<Doctor> findByProficiencyAndIsDeletedFalse(String proficiency);
}