package com.keskin.hospitalapp.mapper;

import com.keskin.hospitalapp.dto.PatientDto;
import com.keskin.hospitalapp.dto.requests.patient.CreatePatientRequestDto;
import com.keskin.hospitalapp.dto.requests.patient.UpdatePatientRequestDto;
import com.keskin.hospitalapp.entity.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PatientMapper {

    PatientDto entityToDto(Patient patient);

    Patient dtoToEntity(PatientDto dto);

    Patient createRequestDtoToEntity(CreatePatientRequestDto requestDto);

    @Mapping(target = "id", ignore = true)
    void updateRequestDtoToEntity(UpdatePatientRequestDto dto, @MappingTarget Patient patient);

}
