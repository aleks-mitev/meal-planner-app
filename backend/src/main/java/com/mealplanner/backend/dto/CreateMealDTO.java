package com.mealplanner.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.util.Map;

@Data
public class CreateMealDTO {

    private String userId; // from URL

    @NotBlank
    private String name;
    private String description; // optional

    @NotEmpty
    private Map<@NotBlank String , @NotNull @PositiveOrZero Double> products;
}
