package com.keskin.hospitalapp.services.impl;

import com.keskin.hospitalapp.dtos.dto.PatientDto;
import com.keskin.hospitalapp.dtos.requests.patient.UpdatePatientRequestDto;
import com.keskin.hospitalapp.entities.Patient;
import com.keskin.hospitalapp.exceptions.*;
import com.keskin.hospitalapp.mapper.PatientMapper;
import com.keskin.hospitalapp.repositories.PatientRepository;
import com.keskin.hospitalapp.services.IPatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PatientServiceImpl implements IPatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;


    @Override
    public List<PatientDto> getAllPatients() {
        return patientRepository.findAll()
                .stream()
                .map(patientMapper::entityToDto)
                .toList();
    }

    @Override
    public Optional<Patient> findPatientByNationalId(String nationalId) {
        return patientRepository.findByNationalId(nationalId);
    }

    @Override
    public Optional<Patient> findByPhoneNumber(String phoneNumber) {
        return patientRepository.findByPhoneNumber(phoneNumber);
    }

    @Override
    public PatientDto updatePatient(UpdatePatientRequestDto requestDto, Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient", "id", id.toString()));

        findByPhoneNumber(requestDto.getPhoneNumber())
                .filter(p -> !p.getId().equals(id))
                .ifPresent(p -> {
                    throw new ResourceAlreadyExistsException("Phone number " , requestDto.getPhoneNumber());
                });


        patientMapper.updateRequestDtoToEntity(requestDto, patient);


        Patient updatedPatient = patientRepository.save(patient);

        return patientMapper.entityToDto(updatedPatient);
    }

    @Override
    public void deletePatient(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient", "id", id.toString()));

        patient.softDelete();
        patientRepository.save(patient);
    }

}
