package com.keskin.hospitalapp.service;

import com.keskin.hospitalapp.dtos.DoctorDto;
import com.keskin.hospitalapp.dtos.PatientDto;
import com.keskin.hospitalapp.dtos.requests.doctor.ChangePasswordRequest;
import com.keskin.hospitalapp.dtos.requests.doctor.CreateDoctorRequestDto;
import com.keskin.hospitalapp.dtos.requests.doctor.UpdateDoctorRequestDto;
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

    void addPatientToDoctor(Long doctorId, Long patientId);

    void changeDoctorPassword(Long doctorId, ChangePasswordRequest request);

    void removePatientFromDoctor(Long doctorId, Long patientId);

}
