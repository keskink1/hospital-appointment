package com.keskin.hospitalapp.mapper;

import com.keskin.hospitalapp.dtos.DoctorDto;
import com.keskin.hospitalapp.dtos.PatientDto;
import com.keskin.hospitalapp.dtos.requests.doctor.CreateDoctorRequestDto;
import com.keskin.hospitalapp.dtos.requests.doctor.UpdateDoctorRequestDto;
import com.keskin.hospitalapp.entities.Doctor;
import com.keskin.hospitalapp.entities.Patient;
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

    @Mapping(target = "password", ignore = true)
    Doctor createRequestToEntity(CreateDoctorRequestDto request);


    void updateRequestDtoToEntity(UpdateDoctorRequestDto dto, @MappingTarget Doctor doctor);

}
