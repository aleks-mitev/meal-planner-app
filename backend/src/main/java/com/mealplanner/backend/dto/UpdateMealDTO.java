package com.mealplanner.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.util.Map;

@Data
public class UpdateMealDTO {
    @NotBlank
    private String name;
    private String description;

    @NotEmpty
    private Map<@NotBlank String , @PositiveOrZero Integer> products;

}
