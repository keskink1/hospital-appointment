package com.keskin.hospitalapp.service;

import com.keskin.hospitalapp.dtos.PatientDto;
import com.keskin.hospitalapp.dtos.requests.patient.CreatePatientRequestDto;
import com.keskin.hospitalapp.dtos.requests.patient.UpdatePatientRequestDto;
import com.keskin.hospitalapp.entity.Patient;

import java.util.List;
import java.util.Optional;

public interface IPatientService {

    List<PatientDto> getAllPatients();

    Optional<Patient> findPatientByNationalId(String nationalId);

    Optional<Patient> findByPhoneNumber(String phoneNumber);

    PatientDto createNewPatient(CreatePatientRequestDto requestDto);

    PatientDto updatePatient(UpdatePatientRequestDto requestDto, Long id);

    void deletePatient(Long id);

}
