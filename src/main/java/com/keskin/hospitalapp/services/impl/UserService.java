package com.keskin.hospitalapp.services.impl;

import com.keskin.hospitalapp.dtos.DoctorDto;
import com.keskin.hospitalapp.dtos.PatientDto;
import com.keskin.hospitalapp.dtos.requests.doctor.CreateDoctorRequestDto;
import com.keskin.hospitalapp.dtos.requests.patient.CreatePatientRequestDto;
import com.keskin.hospitalapp.entities.Doctor;
import com.keskin.hospitalapp.entities.Patient;
import com.keskin.hospitalapp.entities.Role;
import com.keskin.hospitalapp.exceptions.EmailAlreadyExistsException;
import com.keskin.hospitalapp.exceptions.NationalIdAlreadyExistsException;
import com.keskin.hospitalapp.exceptions.PhoneNumberAlreadyExistsException;
import com.keskin.hospitalapp.exceptions.RegistrationNumberAlreadyExists;
import com.keskin.hospitalapp.mapper.DoctorMapper;
import com.keskin.hospitalapp.mapper.PatientMapper;
import com.keskin.hospitalapp.repositories.DoctorRepository;
import com.keskin.hospitalapp.repositories.PatientRepository;
import com.keskin.hospitalapp.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final PatientMapper patientMapper;
    private final DoctorMapper doctorMapper;
    private final PasswordEncoder passwordEncoder;

    private void checkUniquePatientFields(CreatePatientRequestDto request) {
        patientRepository.findByNationalId(request.getNationalId())
                .ifPresent(d -> {
                    throw new NationalIdAlreadyExistsException("National id number already exists! " + request.getNationalId());
                });

        patientRepository.findByPhoneNumber(request.getPhoneNumber())
                .ifPresent(d -> {
                    throw new PhoneNumberAlreadyExistsException("Phone number already exists! " + request.getPhoneNumber());
                });
    }

    private void checkUniqueDoctorFields(CreateDoctorRequestDto request) {
        doctorRepository.findByRegistrationNumber(request.getRegistrationNumber())
                .ifPresent(d -> {
                    throw new RegistrationNumberAlreadyExists("Registration number already exists! " + request.getRegistrationNumber());
                });

        doctorRepository.findByEmail(request.getEmail())
                .ifPresent(d -> {
                    throw new EmailAlreadyExistsException("Email already exists! " + request.getEmail());
                });

        doctorRepository.findByPhoneNumber(request.getPhoneNumber())
                .ifPresent(d -> {
                    throw new PhoneNumberAlreadyExistsException("Phone number already exists! " + request.getPhoneNumber());
                });
    }


    @Override
    public PatientDto registerPatient(CreatePatientRequestDto request) {
        checkUniquePatientFields(request);
        Patient patient = patientMapper.createRequestDtoToEntity(request);

        patient.setPassword(passwordEncoder.encode(request.getPassword()));

        patient.setRole(Role.USER);

        patient = patientRepository.save(patient);

        return patientMapper.entityToDto(patient);
    }

    @Override
    public DoctorDto registerDoctor(CreateDoctorRequestDto request) {
        checkUniqueDoctorFields(request);
        Doctor doctor = doctorMapper.createRequestToEntity(request);

        doctor.setPassword(passwordEncoder.encode(request.getPassword()));

        doctor.setRole(Role.ADMIN);

        doctor = doctorRepository.save(doctor);

        return doctorMapper.entityToDto(doctor);
    }
}
