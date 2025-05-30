package com.mealplanner.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class CreateDailyLogDTO {

    private String userId;

    private List<String> selectedMealIds;
}
