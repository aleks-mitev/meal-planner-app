package com.mealplanner.backend.mapper;

import com.mealplanner.backend.dto.CreateUserDTO;
import com.mealplanner.backend.dto.UpdateUserDTO;
import com.mealplanner.backend.dto.UserResponseDTO;
import com.mealplanner.backend.model.User;
import org.mapstruct.*;

import java.time.LocalDate;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "registrationDate", expression = "java(java.time.LocalDate.now())")
    User toEntity(CreateUserDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "registrationDate", ignore = true)
    @Mapping(target = "password", ignore = true)
    void updateEntity(@MappingTarget User user, UpdateUserDTO dto);

    UserResponseDTO toResponseDTO(User user);
}
