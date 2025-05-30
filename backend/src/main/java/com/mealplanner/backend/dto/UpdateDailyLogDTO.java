package com.mealplanner.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class UpdateDailyLogDTO {
    @NotNull
    private List<@NotBlank String> selectedMealIds;
}
