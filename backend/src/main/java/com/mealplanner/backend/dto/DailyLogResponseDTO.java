package com.mealplanner.backend.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class DailyLogResponseDTO {
    private String id;
    private String userId;
    private LocalDate date;
    private List<String> selectedMealIds;

    private Double totalCalories;
    private Double totalProtein;
    private Double totalFat;
    private Double totalCarbs;
    private Double totalPrice;
}

