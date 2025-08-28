package com.keskin.hospitalapp.services;

import com.keskin.hospitalapp.dtos.PatientDto;
import com.keskin.hospitalapp.dtos.requests.patient.UpdatePatientRequestDto;
import com.keskin.hospitalapp.entities.Patient;

import java.util.List;
import java.util.Optional;

public interface IPatientService {

    List<PatientDto> getAllPatients();

    Optional<Patient> findPatientByNationalId(String nationalId);

    Optional<Patient> findByPhoneNumber(String phoneNumber);

    PatientDto updatePatient(UpdatePatientRequestDto requestDto, Long id);

    void deletePatient(Long id);

}
