package com.mealplanner.backend.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserResponseDTO {

    private String id;
    private String name;
    private String email;
    private LocalDate registrationDate;
}

