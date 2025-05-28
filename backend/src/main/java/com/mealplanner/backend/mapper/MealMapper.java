package com.mealplanner.backend.mapper;

import com.mealplanner.backend.dto.CreateMealDTO;
import com.mealplanner.backend.dto.MealResponseDTO;
import com.mealplanner.backend.dto.UpdateMealDTO;
import com.mealplanner.backend.model.Meal;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface MealMapper {

    Meal toEntity(CreateMealDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget Meal meal, UpdateMealDTO dto);

    MealResponseDTO toResponseDTO(Meal meal);
}
