package com.mealplanner.backend.service;

import com.mealplanner.backend.dto.CreateProductDTO;
import com.mealplanner.backend.dto.UpdateProductDTO;
import com.mealplanner.backend.dto.ProductResponseDTO;
import com.mealplanner.backend.exception.ResourceNotFoundException;
import com.mealplanner.backend.mapper.ProductMapper;
import com.mealplanner.backend.model.Product;
import com.mealplanner.backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductResponseDTO create(CreateProductDTO dto) {
        Product product = productMapper.toEntity(dto);
        Product saved = productRepository.save(product);
        return productMapper.toResponseDTO(saved);
    }

    public List<ProductResponseDTO> getAllByUser(String userId) {
        return productRepository.findAllByUserId(userId)
                .stream()
                .map(productMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Product getById(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    public ProductResponseDTO update(String id, UpdateProductDTO dto) {
        Product product = getById(id);
        productMapper.updateEntity(product, dto);
        Product saved = productRepository.save(product);
        return productMapper.toResponseDTO(saved);
    }

    public void delete(String id) {
        productRepository.deleteById(id);
    }
}
