package com.mealplanner.backend.dto;

import lombok.Data;

@Data
public class ProductResponseDTO {

    private String id;
    private String userId;
    private String name;
    private String description;
    private Double caloriesPer100g;
    private Double proteinPer100g;
    private Double fatPer100g;
    private Double carbsPer100g;
    private Double packageWeightGrams;
    private Double packagePrice;
}
