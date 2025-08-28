package com.keskin.hospitalapp.services;

import com.keskin.hospitalapp.dtos.DoctorDto;
import com.keskin.hospitalapp.dtos.PatientDto;
import com.keskin.hospitalapp.dtos.requests.doctor.CreateDoctorRequestDto;
import com.keskin.hospitalapp.dtos.requests.patient.CreatePatientRequestDto;


public interface IUserService {

    PatientDto registerPatient(CreatePatientRequestDto request);

    DoctorDto registerDoctor(CreateDoctorRequestDto request);
}
