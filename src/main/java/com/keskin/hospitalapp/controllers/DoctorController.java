package com.keskin.hospitalapp.controllers;

import com.keskin.hospitalapp.dtos.DoctorDto;
import com.keskin.hospitalapp.dtos.PatientDto;
import com.keskin.hospitalapp.dtos.requests.doctor.ChangePasswordRequest;
import com.keskin.hospitalapp.dtos.requests.doctor.CreateDoctorRequestDto;
import com.keskin.hospitalapp.dtos.requests.doctor.UpdateDoctorRequestDto;
import com.keskin.hospitalapp.dtos.responses.ApiResponseDto;
import com.keskin.hospitalapp.service.IDoctorService;
import com.keskin.hospitalapp.utils.MessageResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/doctors", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
@Validated
@Tag(
        name = "REST APIs for hospital app",
        description = "CRUD REST APIs to manage doctors"
)
public class DoctorController {

    private final IDoctorService doctorService;
    private final MessageResponseUtil responseUtil;

    @GetMapping
    public ResponseEntity<ApiResponseDto<List<DoctorDto>>> getAllDoctors(Locale locale) {

        List<DoctorDto> doctorDto = doctorService.getAllDoctors();

        return responseUtil.createResponse(
                HttpStatus.OK,
                "doctor.request.success.message",
                doctorDto,
                locale
        );
    }

    @Operation(
            summary = "Create a new doctor",
            description = "REST API to create a new doctor in the hospital system"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Doctor created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/createDoctor")
    public ResponseEntity<ApiResponseDto<DoctorDto>> createDoctor(Locale locale, @Valid @RequestBody CreateDoctorRequestDto request) {
        DoctorDto dto = doctorService.createDoctor(request);

        return responseUtil.createResponse(
                HttpStatus.CREATED,
                "doctor.created.message",
                dto,
                locale
        );
    }

    @Operation(
            summary = "Update a doctor",
            description = "REST API to update an existing doctor's details by ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctor updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Doctor not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/updateDoctor/{id}")
    public ResponseEntity<ApiResponseDto<DoctorDto>> updateDoctor(Locale locale, @Valid @RequestBody UpdateDoctorRequestDto request, @PathVariable Long id) {
        DoctorDto dto = doctorService.updateDoctor(request, id);

        return responseUtil.createResponse(
                HttpStatus.OK,
                "doctor.request.success.message",
                dto,
                locale
        );
    }

    @Operation(
            summary = "Delete a doctor",
            description = "REST API to delete a doctor by ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Doctor deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Doctor not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/deleteDoctor/{id}")
    public ResponseEntity<ApiResponseDto<Void>> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);

        return ResponseEntity.noContent().build();
    }


    @Operation(
            summary = "Get all users",
            description = "REST API to get all patients of the chosen doctor"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status success"
    )
    @GetMapping("/{doctorId}/patients")
    public ResponseEntity<ApiResponseDto<List<PatientDto>>> getPatientsByDoctorId(
            @PathVariable Long doctorId,
            Locale locale
    ) {
        List<PatientDto> patients = doctorService.getPatientsByDoctorId(doctorId);

        return responseUtil.createResponse(
                HttpStatus.OK,
                "doctor.request.success.message",
                patients,
                locale
        );
    }


    @PatchMapping("/{id}/change-password")
    public ResponseEntity<Void> changePassword(
            @PathVariable Long id,
            @Valid @RequestBody ChangePasswordRequest request
    ) {
        doctorService.changeDoctorPassword(id, request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{doctorId}/patients/{patientId}")
    public ResponseEntity<Void> addPatient(
            @PathVariable Long doctorId,
            @PathVariable Long patientId
    ) {
        doctorService.addPatientToDoctor(doctorId, patientId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{doctorId}/patients/{patientId}")
    public ResponseEntity<Void> removePatient(
            @PathVariable Long doctorId,
            @PathVariable Long patientId
    ) {
        doctorService.removePatientFromDoctor(doctorId, patientId);
        return ResponseEntity.noContent().build();
    }

}
