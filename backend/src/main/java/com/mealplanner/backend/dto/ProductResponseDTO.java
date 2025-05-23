package com.mealplanner.backend.dto;

import lombok.Data;

@Data
public class ProductResponseDTO {

    private String id;
    private String name;
    private Double caloriesPer100g;
    private Double proteinPer100g;
    private Double fatPer100g;
    private Double carbsPer100g;
    private Double packageWeightGrams;
    private Double packagePrice;
    private String description;
    private String userId;
}
