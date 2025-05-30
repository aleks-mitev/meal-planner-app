package com.mealplanner.backend.repository;

import com.mealplanner.backend.dto.CreateDailyLogDTO;
import com.mealplanner.backend.dto.DailyLogResponseDTO;
import com.mealplanner.backend.dto.UpdateDailyLogDTO;
import com.mealplanner.backend.model.DailyLog;
import com.mealplanner.backend.model.Meal;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MealRepository extends MongoRepository <Meal, String>{
    List<Meal> findAllByUserId(String userId);
    boolean existsByNameAndUserId(String name, String userId);
    boolean existsByIdAndUserId(String id, String userId);
}
