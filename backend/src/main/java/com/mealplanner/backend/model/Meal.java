package com.mealplanner.backend.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(collection = "meals")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Meal {
    @Id
    private String id;

    @NotBlank
    private String name;

    @NotEmpty
    private Map<@NotBlank String , @NotNull @PositiveOrZero Double> products;

    private Double calories;
    private Double protein;
    private Double fat;
    private Double carbs;
    private Double price;
    private String description;

    @NotBlank
    private String userId;
}
