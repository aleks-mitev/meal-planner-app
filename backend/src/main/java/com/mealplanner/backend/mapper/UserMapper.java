package com.mealplanner.backend.mapper;

import com.mealplanner.backend.dto.CreateUserDTO;
import com.mealplanner.backend.dto.UpdateUserDTO;
import com.mealplanner.backend.dto.UserResponseDTO;
import com.mealplanner.backend.model.User;
import org.mapstruct.*;

import java.time.LocalDate;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // Map CreateUserDTO → User, and set registrationDate manually
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "registrationDate", expression = "java(java.time.LocalDate.now())")
    User toEntity(CreateUserDTO dto);

    // Apply UpdateUserDTO onto existing User
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget User user, UpdateUserDTO dto);

    // Map User → UserResponseDTO
    UserResponseDTO toResponseDTO(User user);
}
