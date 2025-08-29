package com.keskin.hospitalapp.mapper;

import com.keskin.hospitalapp.dtos.dto.PatientDto;
import com.keskin.hospitalapp.dtos.requests.patient.CreatePatientRequestDto;
import com.keskin.hospitalapp.dtos.requests.patient.UpdatePatientRequestDto;
import com.keskin.hospitalapp.entities.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PatientMapper {

    PatientDto entityToDto(Patient patient);

    @Mapping(target = "password", ignore = true)
    Patient createRequestDtoToEntity(CreatePatientRequestDto requestDto);

     void updateRequestDtoToEntity(UpdatePatientRequestDto dto, @MappingTarget Patient patient);

}


