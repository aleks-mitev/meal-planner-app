package com.mealplanner.backend.controller;

import com.mealplanner.backend.dto.CreateProductDTO;
import com.mealplanner.backend.dto.ProductResponseDTO;
import com.mealplanner.backend.dto.UpdateProductDTO;
import com.mealplanner.backend.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v0/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ProductResponseDTO create(@RequestBody @Valid CreateProductDTO dto) {
        return productService.create(dto);
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
