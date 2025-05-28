package com.mealplanner.backend.controller;

import com.mealplanner.backend.dto.*;
import com.mealplanner.backend.service.MealService;
import com.mealplanner.backend.service.ProductService;
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
    private final ProductService productService;
    private final MealService mealService;

    @PostMapping
    public UserResponseDTO createUser(@RequestBody @Valid CreateUserDTO dto) {
        return userService.create(dto);
    }

    @GetMapping
    public List<UserResponseDTO> getAllUsers() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public UserResponseDTO getUserById(@PathVariable String id) {
        return userService.getDTOById(id);
    }

    @PutMapping("/{id}")
    public UserResponseDTO updateUser(@PathVariable String id, @RequestBody @Valid UpdateUserDTO dto) {
        return userService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id) {
        userService.delete(id);
    }

    @GetMapping("/{id}/products")
    public List<ProductResponseDTO> getAllProductsForUser(@PathVariable String id) {
        return productService.getAllByUser(id);
    }

    @GetMapping("/{id}/meals")
    public List<MealResponseDTO> getAllMealsForUser(@PathVariable String id) {
        return mealService.getAllByUser(id);
    }

}


