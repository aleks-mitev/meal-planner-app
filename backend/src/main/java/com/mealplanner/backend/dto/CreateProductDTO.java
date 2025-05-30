package com.mealplanner.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class CreateProductDTO {

    private String userId; // from URL

    @NotBlank
    private String name;
    private String description; // optional

    @Positive(message = "{product.calories.required}")
    private Double caloriesPer100g;
    @PositiveOrZero private Double proteinPer100g;
    @PositiveOrZero private Double fatPer100g;
    @PositiveOrZero private Double carbsPer100g;
    @PositiveOrZero private Double packageWeightGrams;
    @PositiveOrZero private Double packagePrice;
}

