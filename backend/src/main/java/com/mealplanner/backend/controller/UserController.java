package com.mealplanner.backend.controller;

import com.mealplanner.backend.dto.CreateUserDTO;
import com.mealplanner.backend.dto.UpdateUserDTO;
import com.mealplanner.backend.dto.UserResponseDTO;
import com.mealplanner.backend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v0/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public UserResponseDTO createUser(@RequestBody @Valid CreateUserDTO dto) {
        return userService.create(dto);
    }

    @GetMapping
    public List<UserResponseDTO> getAllUsers() {
        return userService.getAll();
    }

    @GetMapping("/{userId}")
    public UserResponseDTO getUserById(@PathVariable String userId) {
        return userService.getDTOById(userId);
    }

    @PutMapping("/{userId}")
    public UserResponseDTO updateUser(@PathVariable String userId, @RequestBody @Valid UpdateUserDTO dto) {
        return userService.update(userId, dto);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable String userId) {
        userService.delete(userId);
    }
}