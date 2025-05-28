package com.mealplanner.backend.mapper;

import com.mealplanner.backend.dto.CreateMealDTO;
import com.mealplanner.backend.dto.MealResponseDTO;
import com.mealplanner.backend.dto.UpdateMealDTO;
import com.mealplanner.backend.model.Meal;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface MealMapper {

    @Mapping(target = "id", ignore = true)
    Meal toEntity(CreateMealDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    void updateEntity(@MappingTarget Meal meal, UpdateMealDTO dto);

    MealResponseDTO toResponseDTO(Meal meal);
}
