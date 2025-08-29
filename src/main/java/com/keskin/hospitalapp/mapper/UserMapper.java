package com.keskin.hospitalapp.mapper;

import com.keskin.hospitalapp.dtos.dto.UserDto;
import com.keskin.hospitalapp.entities.AppUser;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    UserDto entityToDto(AppUser user);
}
