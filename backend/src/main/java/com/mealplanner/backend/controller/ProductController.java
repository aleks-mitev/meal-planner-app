package com.mealplanner.backend.controller;

import com.mealplanner.backend.dto.CreateProductDTO;
import com.mealplanner.backend.dto.ProductResponseDTO;
import com.mealplanner.backend.dto.UpdateProductDTO;
import com.mealplanner.backend.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v0/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ProductResponseDTO create(@RequestBody @Valid CreateProductDTO dto) {
        return productService.create(dto);
    }

    @GetMapping("/user/{userId}")
    public List<ProductResponseDTO> getAllByUser(@PathVariable String userId) {
        return productService.getAllByUser(userId);
    }

    @PutMapping("/{id}")
    public ProductResponseDTO update(@PathVariable String id, @RequestBody @Valid UpdateProductDTO dto) {
        return productService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        productService.delete(id);
    }
}
