package com.mealplanner.backend.dto;

import lombok.Data;

import java.util.Map;

@Data
public class MealResponseDTO {
    private String id;
    private String userId;
    private String name;
    private String description;
    private Map<String, Integer> products;
}
