package com.mealplanner.backend.mapper;

import com.mealplanner.backend.dto.CreateUserDTO;
import com.mealplanner.backend.dto.UpdateUserDTO;
import com.mealplanner.backend.dto.UserResponseDTO;
import com.mealplanner.backend.model.User;
import org.mapstruct.*;

import java.time.LocalDate;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(CreateUserDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget User user, UpdateUserDTO dto);

    UserResponseDTO toResponseDTO(User user);
}
