package com.mealplanner.backend.controller;

import com.mealplanner.backend.dto.CreateMealDTO;
import com.mealplanner.backend.dto.MealResponseDTO;
import com.mealplanner.backend.dto.UpdateMealDTO;
import com.mealplanner.backend.service.MealService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v0/meals")
@RequiredArgsConstructor
public class MealController {

    private final MealService mealService;

    @PostMapping
    public MealResponseDTO createMeal(@RequestBody @Valid CreateMealDTO dto) {
        return mealService.create(dto);
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
