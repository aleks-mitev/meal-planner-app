package com.mealplanner.backend.repository;

import com.mealplanner.backend.model.Meal;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MealRepository extends MongoRepository <Meal, String>{
    List<Meal> findAllByUserId(String userId);
    boolean existsByNameAndUserId(String name, String userId);
}
