package com.mealplanner.backend.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private String id;

    @NotBlank(message = "{user.name.required}")
    private String name;

    @Email(message = "{user.email.invalid}")
    @NotBlank(message = "{user.email.required}")
    private String email;

    @NotBlank(message = "{user.password.required}")
    @Size(min = 5, message = "{user.password.short}")
    private String password;

    private LocalDate registrationDate = LocalDate.now();
}


