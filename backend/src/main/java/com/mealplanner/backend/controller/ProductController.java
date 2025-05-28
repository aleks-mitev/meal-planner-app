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
@RequestMapping("/api/v0/users/{userId}/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ProductResponseDTO create(@PathVariable String userId, @RequestBody @Valid CreateProductDTO dto) {
        dto.setUserId(userId);
        return productService.create(dto);
    }

    @GetMapping
    public List<ProductResponseDTO> getAllProductsForUser(@PathVariable String userId) {
        return productService.getAllByUser(userId);
    }

    @GetMapping("/{productId}")
    public ProductResponseDTO getById(@PathVariable String productId) {
        return productService.getDTOById(productId);
    }

    @PutMapping("/{productId}")
    public ProductResponseDTO update(@PathVariable String productId, @RequestBody @Valid UpdateProductDTO dto) {
        return productService.update(productId, dto);
    }

    @DeleteMapping("/{productId}")
    public void delete(@PathVariable String productId) {
        productService.delete(productId);
    }
}
