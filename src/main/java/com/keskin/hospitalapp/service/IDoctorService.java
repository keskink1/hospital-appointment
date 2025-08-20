package com.keskin.hospitalapp.service;

import com.keskin.hospitalapp.dto.DoctorDto;
import com.keskin.hospitalapp.dto.PatientDto;
import com.keskin.hospitalapp.dto.requests.doctor.CreateDoctorRequestDto;
import com.keskin.hospitalapp.dto.requests.doctor.UpdateDoctorRequestDto;
import com.keskin.hospitalapp.entity.Doctor;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;
import java.util.Optional;

public interface IDoctorService {

    List<DoctorDto> getAllDoctors();

    Optional<Doctor> getDoctorByPhoneNumber(String phoneNumber);

    List<Doctor> getDoctorsByDepartment(String department);

    List<Doctor> getDoctorsByProficiency(String proficiency);

    Optional<Doctor> findByRegistrationNumber(String registrationNumber);

    Optional<Doctor> findByEmail(String email);

    Optional<Doctor> findByPhoneNumber(String phoneNumber);

    DoctorDto createDoctor(CreateDoctorRequestDto request);

    DoctorDto updateDoctor(UpdateDoctorRequestDto request, Long id);

    void deleteDoctor(Long id);

    @EntityGraph(attributePaths = "patients")
    List<PatientDto> getPatientsByDoctorId(Long doctorId);

}
