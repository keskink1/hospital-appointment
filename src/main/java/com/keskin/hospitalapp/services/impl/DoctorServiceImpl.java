package com.keskin.hospitalapp.services.impl;

import com.keskin.hospitalapp.dtos.DoctorAdminViewDto;
import com.keskin.hospitalapp.dtos.DoctorDto;
import com.keskin.hospitalapp.dtos.PatientDto;
import com.keskin.hospitalapp.dtos.requests.doctor.ChangePasswordRequest;
import com.keskin.hospitalapp.dtos.requests.doctor.UpdateDoctorRequestDto;
import com.keskin.hospitalapp.entities.Doctor;
import com.keskin.hospitalapp.entities.Patient;
import com.keskin.hospitalapp.exceptions.*;
import com.keskin.hospitalapp.mapper.DoctorMapper;
import com.keskin.hospitalapp.mapper.PatientMapper;
import com.keskin.hospitalapp.repositories.DoctorRepository;
import com.keskin.hospitalapp.repositories.PatientRepository;
import com.keskin.hospitalapp.services.IDoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class DoctorServiceImpl implements IDoctorService {

    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final DoctorMapper doctorMapper;
    private final PatientMapper patientMapper;
    private final PasswordEncoder passwordEncoder;


    private void checkUniqueDoctorFieldsForUpdate(UpdateDoctorRequestDto request, Long doctorId) {
        findByEmail(request.getEmail())
                .filter(d -> !d.getId().equals(doctorId))
                .ifPresent(d -> {
                    throw new ResourceAlreadyExistsException("Email  " ,request.getEmail());
                });

        findByPhoneNumber(request.getPhoneNumber())
                .filter(d -> !d.getId().equals(doctorId))
                .ifPresent(d -> {
                    throw new ResourceAlreadyExistsException("Phone number " , request.getPhoneNumber());
                });
    }


    @Override
    public List<DoctorDto> getAllDoctors() {

        return doctorRepository.findAll()
                .stream()
                .map(doctorMapper::entityToDto)
                .toList();
    }

    @Override
    public List<DoctorAdminViewDto> getAllDoctorsForAdmin() {
        return doctorRepository.findAll()
                .stream()
                .map(doctorMapper::entityToAdminDto)
                .toList();
    }

    @Override
    public Optional<Doctor> getDoctorByPhoneNumber(String phoneNumber) {
        return doctorRepository.findByPhoneNumber(phoneNumber);
    }

    @Override
    public List<Doctor> getDoctorsByDepartment(String department) {
        return doctorRepository.findByDepartment(department);
    }

    @Override
    public List<Doctor> getDoctorsByProficiency(String proficiency) {
        return doctorRepository.findByProficiency(proficiency);
    }

    @Override
    public DoctorDto getDoctorById(Long id) {
        Doctor doctor = doctorRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Doctor", "ID", id.toString()));

        return doctorMapper.entityToDto(doctor);
    }

    @Override
    public Optional<Doctor> findByRegistrationNumber(String registrationNumber) {
        return doctorRepository.findByRegistrationNumber(registrationNumber);
    }

    @Override
    public Optional<Doctor> findByEmail(String email) {
        return doctorRepository.findByEmail(email);
    }

    @Override
    public Optional<Doctor> findByPhoneNumber(String phoneNumber) {
        return doctorRepository.findByPhoneNumber(phoneNumber);
    }

    @Override
    public DoctorDto updateDoctor(UpdateDoctorRequestDto request, Long id) {
        Doctor doctor = doctorRepository.findById(id)
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

        doctor.changePassword(request.getOldPassword(), request.getNewPassword(), passwordEncoder);
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
