package com.mealplanner.backend.dto;

import lombok.Data;

import java.util.Map;

@Data
public class MealResponseDTO {
    private String id;
    private String name;
    private Map<String, Integer> products;
    private String description;
    private String userId;
}
