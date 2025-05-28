package com.mealplanner.backend.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "daily_logs")
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
    private List<@NotBlank String> selectedMealIds = new ArrayList<>();

    private Double totalCalories = 0.0;
    private Double totalProtein = 0.0;
    private Double totalFat = 0.0;
    private Double totalCarbs = 0.0;
    private Double totalPrice = 0.0;

}
