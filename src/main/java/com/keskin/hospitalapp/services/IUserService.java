package com.keskin.hospitalapp.services;

import com.keskin.hospitalapp.dtos.dto.DoctorDto;
import com.keskin.hospitalapp.dtos.dto.PatientDto;
import com.keskin.hospitalapp.dtos.requests.doctor.CreateDoctorRequestDto;
import com.keskin.hospitalapp.dtos.requests.patient.CreatePatientRequestDto;

import com.keskin.hospitalapp.entities.AppUser;

import java.util.Optional;


public interface IUserService {

    PatientDto registerPatient(CreatePatientRequestDto request);

    DoctorDto registerDoctor(CreateDoctorRequestDto request);

    Optional<AppUser> findByEmail(String email);

    Optional<AppUser> findByPhoneNumber(String phone);

    Optional<AppUser> findById(Long id);

    Object getMeDtoById(Long id);
}
