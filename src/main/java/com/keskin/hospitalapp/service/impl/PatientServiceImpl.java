package com.keskin.hospitalapp.service.impl;

import com.keskin.hospitalapp.dtos.PatientDto;
import com.keskin.hospitalapp.dtos.requests.patient.CreatePatientRequestDto;
import com.keskin.hospitalapp.dtos.requests.patient.UpdatePatientRequestDto;
import com.keskin.hospitalapp.entity.Patient;
import com.keskin.hospitalapp.exceptions.*;
import com.keskin.hospitalapp.mapper.PatientMapper;
import com.keskin.hospitalapp.repository.PatientRepository;
import com.keskin.hospitalapp.service.IPatientService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class PatientServiceImpl implements IPatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    private void checkUniquePatientFields(CreatePatientRequestDto request) {
        findPatientByNationalId(request.getNationalId())
                .ifPresent(d -> {
                    throw new NationalIdAlreadyExistsException("National id number already exists! " + request.getNationalId());
                });

        findByPhoneNumber(request.getPhoneNumber())
                .ifPresent(d -> {
                    throw new PhoneNumberAlreadyExistsException("Phone number already exists! " + request.getPhoneNumber());
                });
    }

    @Override
    public List<PatientDto> getAllPatients() {
        return patientRepository.findByIsDeletedFalse()
                .stream()
                .map(patientMapper::entityToDto)
                .toList();
    }

    @Override
    public Optional<Patient> findPatientByNationalId(String nationalId) {
        return patientRepository.findByNationalIdAndIsDeletedFalse(nationalId);
    }

    @Override
    public Optional<Patient> findByPhoneNumber(String phoneNumber) {
        return patientRepository.findByPhoneNumberAndIsDeletedFalse(phoneNumber);

    }

    @Override
    public PatientDto createNewPatient(CreatePatientRequestDto requestDto) {
        checkUniquePatientFields(requestDto);
        Patient patient = patientMapper.createRequestDtoToEntity(requestDto);

        Patient savedPatient = patientRepository.save(patient);

        return patientMapper.entityToDto(savedPatient);
    }

    @Override
    public PatientDto updatePatient(UpdatePatientRequestDto requestDto, Long id) {
        Patient patient = patientRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient", "id", id.toString()));

        findByPhoneNumber(requestDto.getPhoneNumber())
                .filter(p -> !p.getId().equals(id))
                .ifPresent(p -> {
                    throw new PhoneNumberAlreadyExistsException("Phone number already exists! " + requestDto.getPhoneNumber());
                });


        patientMapper.updateRequestDtoToEntity(requestDto, patient);


        Patient updatedPatient = patientRepository.save(patient);

        return patientMapper.entityToDto(updatedPatient);
    }

    @Override
    public void deletePatient(Long id) {
        Patient patient = patientRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient", "id", id.toString()));

        patient.softDelete();
        patientRepository.save(patient);
    }


}
