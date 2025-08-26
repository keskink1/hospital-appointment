package com.keskin.hospitalapp.mapper;

import com.keskin.hospitalapp.dtos.PatientDto;
import com.keskin.hospitalapp.dtos.requests.patient.CreatePatientRequestDto;
import com.keskin.hospitalapp.dtos.requests.patient.UpdatePatientRequestDto;
import com.keskin.hospitalapp.entity.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PatientMapper {

    PatientDto entityToDto(Patient patient);

    Patient dtoToEntity(PatientDto dto);

    @Mapping(target = "password", source = "password")
    Patient createRequestDtoToEntity(CreatePatientRequestDto requestDto);

    @Mapping(target = "id", ignore = true)
    void updateRequestDtoToEntity(UpdatePatientRequestDto dto, @MappingTarget Patient patient);

}
