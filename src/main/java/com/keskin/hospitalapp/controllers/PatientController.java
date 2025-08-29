package com.keskin.hospitalapp.controllers;

import com.keskin.hospitalapp.dtos.dto.PatientDto;
import com.keskin.hospitalapp.dtos.requests.patient.UpdatePatientRequestDto;
import com.keskin.hospitalapp.dtos.responses.ApiResponseDto;
import com.keskin.hospitalapp.services.IPatientService;
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
@RequestMapping(value = "/patients",  produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
@Validated
@Tag(
        name = "REST APIs for hospital app",
        description = "CRUD REST APIs to manage patients"
)
public class PatientController {

    private final IPatientService patientService;
    private final MessageResponseUtil responseUtil;

    @GetMapping
    public ResponseEntity<ApiResponseDto<List<PatientDto>>> getAllPatients(Locale locale){
        List<PatientDto> dto =  patientService.getAllPatients();

       return responseUtil.createResponse(
                HttpStatus.OK,
                "patient.request.success.message",
                dto,
                locale
        );
    }

    @Operation(
            summary = "Update a patient",
            description = "REST API to update an existing patient's details by ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Patient not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/updatePatient/{id}")
    public ResponseEntity<ApiResponseDto<PatientDto>> updatePatient(@Valid @RequestBody UpdatePatientRequestDto dto, @PathVariable Long id, Locale locale){
        PatientDto patientDto = patientService.updatePatient(dto, id);

        return responseUtil.createResponse(
                HttpStatus.OK,
                "patient.request.success.message",
                patientDto,
                locale
        );
    }

    @Operation(
            summary = "Delete a patient",
            description = "REST API to delete a patient by ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Patient deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Patient not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PatchMapping("/deletePatient/{id}")
    public ResponseEntity<ApiResponseDto<Void>> deletePatient(@PathVariable Long id){
        patientService.deletePatient(id);

        return ResponseEntity.noContent().build();
    }
}
