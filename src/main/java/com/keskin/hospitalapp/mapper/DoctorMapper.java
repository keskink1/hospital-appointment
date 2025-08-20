package com.keskin.hospitalapp.mapper;

import com.keskin.hospitalapp.dto.DoctorDto;
import com.keskin.hospitalapp.dto.PatientDto;
import com.keskin.hospitalapp.dto.requests.doctor.CreateDoctorRequestDto;
import com.keskin.hospitalapp.dto.requests.doctor.UpdateDoctorRequestDto;
import com.keskin.hospitalapp.dto.requests.patient.UpdatePatientRequestDto;
import com.keskin.hospitalapp.entity.Doctor;
import com.keskin.hospitalapp.entity.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.Set;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DoctorMapper {

    DoctorDto entityToDto(Doctor doctor);

    Doctor dtoToEntity(DoctorDto doctorDto);

    Set<PatientDto> patientsToPatientDtos(Set<Patient> patients);

    Doctor createRequestToEntity(CreateDoctorRequestDto request);

    DoctorDto updateRequestToDto(UpdateDoctorRequestDto request);

    @Mapping(target = "id", ignore = true)
    void updateRequestDtoToEntity(UpdateDoctorRequestDto dto, @MappingTarget Doctor doctor);
}
