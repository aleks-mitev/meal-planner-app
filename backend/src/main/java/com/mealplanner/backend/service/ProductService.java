package com.mealplanner.backend.service;

import com.mealplanner.backend.dto.CreateProductDTO;
import com.mealplanner.backend.dto.UpdateProductDTO;
import com.mealplanner.backend.dto.ProductResponseDTO;
import com.mealplanner.backend.exception.ResourceNotFoundException;
import com.mealplanner.backend.mapper.ProductMapper;
import com.mealplanner.backend.model.Product;
import com.mealplanner.backend.repository.ProductRepository;
import com.mealplanner.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ProductMapper productMapper;

    public ProductResponseDTO create(CreateProductDTO dto) {
        if (!userRepository.existsById(dto.getUserId())) {
            throw new ResourceNotFoundException("User not found for userId: " + dto.getUserId());
        }
        
        if (productRepository.existsByNameAndUserId(dto.getName(), dto.getUserId())) {
            throw new IllegalArgumentException("Product with this name already exists for this user");
        }

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

        if(!product.getName().equals(dto.getName()) &&
           productRepository.existsByNameAndUserId(dto.getName(), product.getUserId())) {
            throw new IllegalArgumentException("Product with this name already exists for this user");
        }

        productMapper.updateEntity(product, dto);
        Product saved = productRepository.save(product);
        return productMapper.toResponseDTO(saved);
    }

    public void delete(String id) {
        productRepository.deleteById(id);
    }
}
