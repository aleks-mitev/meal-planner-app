package com.mealplanner.backend.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "daily_logs")
@CompoundIndex(name = "unique_user_date", def = "{'userId': 1, 'date': 1}", unique = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyLog {

    @Id
    private String id;

    @NotBlank
    private String userId;

    @NotNull
    private LocalDate date;

    @NotNull
    private List<@NotBlank String> selectedMealIds;

    private Double totalCalories;
    private Double totalProtein;
    private Double totalFat;
    private Double totalCarbs;
    private Double totalPrice;
}
