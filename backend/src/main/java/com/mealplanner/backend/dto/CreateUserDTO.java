package com.mealplanner.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateUserDTO {

    @NotBlank(message = "{user.name.required}")
    private String name;

    @Email(message = "{user.email.invalid}")
    @NotBlank(message = "{user.email.required}")
    private String email;

    @NotBlank(message = "{user.password.required}")
    @Size(min = 5, message = "{user.password.short}")
    private String password;
}
