package com.keskin.hospitalapp.controllers;

import com.keskin.hospitalapp.dtos.dto.PatientDto;
import com.keskin.hospitalapp.dtos.dto.DoctorDto;
import com.keskin.hospitalapp.dtos.requests.patient.CreatePatientRequestDto;
import com.keskin.hospitalapp.dtos.requests.doctor.CreateDoctorRequestDto;
import com.keskin.hospitalapp.dtos.requests.patient.UpdatePatientRequestDto;
import com.keskin.hospitalapp.dtos.responses.ApiResponseDto;
import com.keskin.hospitalapp.entities.Patient;
import com.keskin.hospitalapp.services.IDoctorService;
import com.keskin.hospitalapp.services.IPatientService;
import com.keskin.hospitalapp.services.IUserService;
import com.keskin.hospitalapp.utils.MessageResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Locale;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final IUserService userService;
    private final IPatientService patientService;
    private final IDoctorService doctorService;
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

    @PutMapping("/patients/me")
    public ResponseEntity<ApiResponseDto<Object>> updateMe(@Valid @RequestBody UpdatePatientRequestDto request, Locale locale) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userId = (Long) authentication.getPrincipal();

        patientService.updatePatient(request, userId);
        var updatedPatientDto = userService.getMeDtoById(userId);


        return responseUtil.createResponse(
                HttpStatus.OK,
                "patient.request.success.message",
                updatedPatientDto,
                locale
        );
    }

    @DeleteMapping("/patients/me")
    public ResponseEntity<Void> deleteMe() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userId = (Long) authentication.getPrincipal();

        patientService.deletePatient(userId);
        return ResponseEntity.noContent().build();
    }

}
