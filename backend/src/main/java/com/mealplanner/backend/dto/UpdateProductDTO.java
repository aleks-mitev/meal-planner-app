package com.mealplanner.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class UpdateProductDTO {

    @NotBlank
    private String name;

    @PositiveOrZero private Double caloriesPer100g;
    @PositiveOrZero private Double proteinPer100g;
    @PositiveOrZero private Double fatPer100g;
    @PositiveOrZero private Double carbsPer100g;
    @PositiveOrZero private Double packageWeightGrams;
    @PositiveOrZero private Double packagePrice;

    private String description;
}

