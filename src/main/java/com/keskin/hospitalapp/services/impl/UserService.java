package com.keskin.hospitalapp.services.impl;

import com.keskin.hospitalapp.dtos.DoctorDto;
import com.keskin.hospitalapp.dtos.PatientDto;
import com.keskin.hospitalapp.dtos.requests.doctor.CreateDoctorRequestDto;
import com.keskin.hospitalapp.dtos.requests.patient.CreatePatientRequestDto;
import com.keskin.hospitalapp.entities.AppUser;
import com.keskin.hospitalapp.entities.Doctor;
import com.keskin.hospitalapp.entities.Patient;
import com.keskin.hospitalapp.entities.Role;
import com.keskin.hospitalapp.exceptions.ResourceAlreadyExistsException;
import com.keskin.hospitalapp.mapper.DoctorMapper;
import com.keskin.hospitalapp.mapper.PatientMapper;
import com.keskin.hospitalapp.repositories.DoctorRepository;
import com.keskin.hospitalapp.repositories.PatientRepository;
import com.keskin.hospitalapp.services.IUserService;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService implements IUserService, UserDetailsService {

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final PatientMapper patientMapper;
    private final DoctorMapper doctorMapper;
    private final PasswordEncoder passwordEncoder;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("AppUser not found!"));

        return new User(
                user.getEmail(),
                user.getPassword(),
                Collections.emptyList()
        );
    }

    private void checkUniquePatientFields(CreatePatientRequestDto request) {
        patientRepository.findByNationalId(request.getNationalId())
                .ifPresent(d -> {
                    throw new ResourceAlreadyExistsException("National id ", request.getNationalId());
                });

        findByEmail(request.getEmail())
                .ifPresent(p -> {
                    throw new ResourceAlreadyExistsException("Email " , request.getEmail());
                });

        patientRepository.findByPhoneNumber(request.getPhoneNumber())
                .ifPresent(d -> {
                    throw new ResourceAlreadyExistsException("Phone number " , request.getPhoneNumber());
                });
    }

    private void checkUniqueDoctorFields(CreateDoctorRequestDto request) {
        doctorRepository.findByRegistrationNumber(request.getRegistrationNumber())
                .ifPresent(d -> {
                    throw new ResourceAlreadyExistsException("Registration number " , request.getRegistrationNumber());
                });

        findByEmail(request.getEmail())
                .ifPresent(d -> {
                    throw new ResourceAlreadyExistsException("Email " , request.getEmail());
                });

        doctorRepository.findByPhoneNumber(request.getPhoneNumber())
                .ifPresent(d -> {
                    throw new ResourceAlreadyExistsException("Phone number " , request.getPhoneNumber());
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

    @Override
    public Optional<AppUser> findByEmail(String email) {
        TypedQuery<AppUser> query = entityManager.createQuery(
                "FROM AppUser u WHERE u.email = :email", AppUser.class
        ).setParameter("email", email);

        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }
}
