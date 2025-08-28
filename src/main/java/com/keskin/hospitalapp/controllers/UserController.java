package com.keskin.hospitalapp.controllers;

import com.keskin.hospitalapp.dtos.PatientDto;
import com.keskin.hospitalapp.dtos.DoctorDto;
import com.keskin.hospitalapp.dtos.requests.patient.CreatePatientRequestDto;
import com.keskin.hospitalapp.dtos.requests.doctor.CreateDoctorRequestDto;
import com.keskin.hospitalapp.services.IUserService;
import com.keskin.hospitalapp.utils.MessageResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Locale;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final IUserService userService;
    private final MessageResponseUtil responseUtil;

    @PostMapping("/register/patient")
    public ResponseEntity<?> registerPatient(
            @Valid @RequestBody CreatePatientRequestDto request,
            UriComponentsBuilder uriBuilder,
            Locale locale
    ) {
        PatientDto patientDto = userService.registerPatient(request);

        var location = uriBuilder
                .path("/api/users/{id}")
                .buildAndExpand(patientDto.getId())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(responseUtil.createResponse(
                        HttpStatus.CREATED,
                        "patient.create.success.message",
                        patientDto,
                        locale
                ));
    }

    @PostMapping("/register/doctor")
    public ResponseEntity<?> registerDoctor(
            @Valid @RequestBody CreateDoctorRequestDto request,
            UriComponentsBuilder uriBuilder,
            Locale locale
    ) {
        DoctorDto doctorDto = userService.registerDoctor(request);

        var location = uriBuilder
                .path("/api/users/{id}")
                .buildAndExpand(doctorDto.getId())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(responseUtil.createResponse(
                        HttpStatus.CREATED,
                        "doctor.created.message",
                        doctorDto,
                        locale
                ));
    }

}
