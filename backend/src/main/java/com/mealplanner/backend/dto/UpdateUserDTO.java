package com.mealplanner.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateUserDTO {

    @NotBlank(message = "{user.name.required}")
    private String name;

    @Email(message = "{user.email.invalid}")
    @NotBlank(message = "{user.email.required}")
    private String email;
}

