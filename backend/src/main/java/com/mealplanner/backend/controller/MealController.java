package com.mealplanner.backend.controller;

import com.mealplanner.backend.dto.CreateMealDTO;
import com.mealplanner.backend.dto.MealResponseDTO;
import com.mealplanner.backend.dto.UpdateMealDTO;
import com.mealplanner.backend.service.MealService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v0/users/{userId}/meals")
@RequiredArgsConstructor
public class MealController {

    private final MealService mealService;

    @PostMapping
    public MealResponseDTO createMeal(@PathVariable String userId, @RequestBody @Valid CreateMealDTO dto) {
        dto.setUserId(userId);
        return mealService.create(dto);
    }

    @GetMapping
    public List<MealResponseDTO> getAllMealsForUser(@PathVariable String userId) {
        return mealService.getAllByUser(userId);
    }

    @GetMapping("/{mealId}")
    public MealResponseDTO getMealById(@PathVariable String mealId) {
       return mealService.getDTOById(mealId);
    }

    @PutMapping("/{mealId}")
    public MealResponseDTO updateMeal(@PathVariable String mealId, @RequestBody @Valid UpdateMealDTO dto) {
        return mealService.update(mealId, dto);
    }

    @DeleteMapping("/{mealId}")
    public void deleteMeal(@PathVariable String mealId) {
        mealService.delete(mealId);
    }
}
