package com.mealplanner.backend.dto;

import lombok.Data;

import java.util.Map;

@Data
public class MealResponseDTO {
    private String id;
    private String userId;
    private String name;
    private String description;
    private Map<String, Double> products;
    private Double calories;
    private Double protein;
    private Double fat;
    private Double carbs;
    private Double price;
}
