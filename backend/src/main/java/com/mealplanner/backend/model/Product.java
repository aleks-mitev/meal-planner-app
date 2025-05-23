package com.mealplanner.backend.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    private String id;

    @NotBlank
    private String name;

    @PositiveOrZero private Double caloriesPer100g;
    @PositiveOrZero private Double proteinPer100g;
    @PositiveOrZero private Double fatPer100g;
    @PositiveOrZero private Double carbsPer100g;
    @PositiveOrZero private Double packageWeightGrams;
    @PositiveOrZero private Double packagePrice;

    private String description;

    @NotBlank
    private String userId;
}
