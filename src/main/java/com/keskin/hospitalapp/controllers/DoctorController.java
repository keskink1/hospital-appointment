package com.keskin.hospitalapp.controllers;

import com.keskin.hospitalapp.dto.DoctorDto;
import com.keskin.hospitalapp.dto.PatientDto;
import com.keskin.hospitalapp.dto.requests.doctor.CreateDoctorRequestDto;
import com.keskin.hospitalapp.dto.requests.doctor.UpdateDoctorRequestDto;
import com.keskin.hospitalapp.dto.responses.ApiResponseDto;
import com.keskin.hospitalapp.service.impl.DoctorServiceImpl;
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
@RequestMapping(value = "/api/doctors",  produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
@Validated
@Tag(
        name = "REST APIs for hospital app",
        description = "CRUD REST APIs to manage doctors"
)
public class DoctorController {

    private final DoctorServiceImpl doctorService;
    private final MessageResponseUtil responseUtil;

    @GetMapping
    public ResponseEntity<ApiResponseDto<List<DoctorDto>>> getAllDoctors(Locale locale){

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
    public ResponseEntity<ApiResponseDto<DoctorDto>> createDoctor(Locale locale, @Valid @RequestBody CreateDoctorRequestDto request){
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
    public ResponseEntity<ApiResponseDto<DoctorDto>> updateDoctor(Locale locale, @Valid @RequestBody UpdateDoctorRequestDto request, @PathVariable Long id){
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
    public ResponseEntity<ApiResponseDto<Void>> deleteDoctor(Locale locale, @PathVariable Long id){
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

}
