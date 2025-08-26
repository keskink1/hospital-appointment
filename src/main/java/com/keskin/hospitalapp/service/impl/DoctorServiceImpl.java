package com.keskin.hospitalapp.service.impl;

import com.keskin.hospitalapp.dtos.DoctorDto;
import com.keskin.hospitalapp.dtos.PatientDto;
import com.keskin.hospitalapp.dtos.requests.doctor.ChangePasswordRequest;
import com.keskin.hospitalapp.dtos.requests.doctor.CreateDoctorRequestDto;
import com.keskin.hospitalapp.dtos.requests.doctor.UpdateDoctorRequestDto;
import com.keskin.hospitalapp.entity.Doctor;
import com.keskin.hospitalapp.entity.Patient;
import com.keskin.hospitalapp.exceptions.*;
import com.keskin.hospitalapp.mapper.DoctorMapper;
import com.keskin.hospitalapp.mapper.PatientMapper;
import com.keskin.hospitalapp.repository.DoctorRepository;
import com.keskin.hospitalapp.repository.PatientRepository;
import com.keskin.hospitalapp.service.IDoctorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class DoctorServiceImpl implements IDoctorService {

    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final DoctorMapper doctorMapper;
    private final PatientMapper patientMapper;

    private void checkUniqueDoctorFields(CreateDoctorRequestDto request) {
        findByRegistrationNumber(request.getRegistrationNumber())
                .ifPresent(d -> {
                    throw new RegistrationNumberAlreadyExists("Registration number already exists! " + request.getRegistrationNumber());
                });

        findByEmail(request.getEmail())
                .ifPresent(d -> {
                    throw new EmailAlreadyExistsException("Email already exists! " + request.getEmail());
                });

        findByPhoneNumber(request.getPhoneNumber())
                .ifPresent(d -> {
                    throw new PhoneNumberAlreadyExistsException("Phone number already exists! " + request.getPhoneNumber());
                });
    }

    private void checkUniqueDoctorFieldsForUpdate(UpdateDoctorRequestDto request, Long doctorId) {
        findByEmail(request.getEmail())
                .filter(d -> !d.getId().equals(doctorId))
                .ifPresent(d -> {
                    throw new EmailAlreadyExistsException("Email already exists! " + request.getEmail());
                });

        findByPhoneNumber(request.getPhoneNumber())
                .filter(d -> !d.getId().equals(doctorId))
                .ifPresent(d -> {
                    throw new PhoneNumberAlreadyExistsException("Phone number already exists! " + request.getPhoneNumber());
                });
    }


    @Override
    public List<DoctorDto> getAllDoctors() {

        return doctorRepository.findByIsDeletedFalse()
                .stream()
                .map(doctorMapper::entityToDto)
                .toList();
    }

    @Override
    public Optional<Doctor> getDoctorByPhoneNumber(String phoneNumber) {
        return doctorRepository.findByPhoneNumberAndIsDeletedFalse(phoneNumber);
    }

    @Override
    public List<Doctor> getDoctorsByDepartment(String department) {
        return doctorRepository.findByDepartmentAndIsDeletedFalse(department);
    }

    @Override
    public List<Doctor> getDoctorsByProficiency(String proficiency) {
        return doctorRepository.findByProficiencyAndIsDeletedFalse(proficiency);
    }


    @Override
    public DoctorDto createDoctor(CreateDoctorRequestDto request) {
        checkUniqueDoctorFields(request);
        Doctor doctor = doctorMapper.createRequestToEntity(request);

        return doctorMapper.entityToDto(doctorRepository.save(doctor));
    }

    @Override
    public Optional<Doctor> findByRegistrationNumber(String registrationNumber) {
        return doctorRepository.findByRegistrationNumberAndIsDeletedFalse(registrationNumber);
    }

    @Override
    public Optional<Doctor> findByEmail(String email) {
        return doctorRepository.findByEmailAndIsDeletedFalse(email);
    }

    @Override
    public Optional<Doctor> findByPhoneNumber(String phoneNumber) {
        return doctorRepository.findByPhoneNumberAndIsDeletedFalse(phoneNumber);
    }

    @Override
    public DoctorDto updateDoctor(UpdateDoctorRequestDto request, Long id) {
        Doctor doctor = doctorRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor", "id", id.toString()));

        checkUniqueDoctorFieldsForUpdate(request, id);

        doctorMapper.updateRequestDtoToEntity(request, doctor);
        Doctor updatedDoctor = doctorRepository.save(doctor);

        return doctorMapper.entityToDto(updatedDoctor);
    }


    @Override
    public void deleteDoctor(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor", "id", id.toString()));

        doctor.softDelete();
        doctorRepository.save(doctor);
    }


    @Override
    public List<PatientDto> getPatientsByDoctorId(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor", "id", doctorId.toString()));

        return doctor.getPatients().stream()
                .map(patientMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void addPatientToDoctor(Long doctorId, Long patientId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor", "id", doctorId.toString()));

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient", "id", patientId.toString()));

        doctor.addPatient(patient);
        doctorRepository.save(doctor);
    }

    @Override
    public void changeDoctorPassword(Long doctorId, ChangePasswordRequest request) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor", "id", doctorId.toString()));

        doctor.changePassword(request.getOldPassword(), request.getNewPassword());
        doctorRepository.save(doctor);
    }

    @Override
    public void removePatientFromDoctor(Long doctorId, Long patientId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor", "id", doctorId.toString()));

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient", "id", patientId.toString()));

        doctor.removePatient(patient);
        doctorRepository.save(doctor);
    }
}
