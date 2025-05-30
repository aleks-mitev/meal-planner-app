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
    private final UserService userService;

    public void applyDefaults(CreateProductDTO dto) {
        if (dto.getCaloriesPer100g() == null) dto.setCaloriesPer100g(0.0);
        if (dto.getProteinPer100g() == null) dto.setProteinPer100g(0.0);
        if (dto.getFatPer100g() == null) dto.setFatPer100g(0.0);
        if (dto.getCarbsPer100g() == null) dto.setCarbsPer100g(0.0);
        if (dto.getPackageWeightGrams() == null) dto.setPackageWeightGrams(0.0);
        if (dto.getPackagePrice() == null) dto.setPackagePrice(0.0);
    }

    public ProductResponseDTO create(CreateProductDTO dto) {
        userService.validateUserExists(dto.getUserId());
        
        if (productRepository.existsByNameAndUserId(dto.getName(), dto.getUserId())) {
            throw new IllegalArgumentException("Product with this name already exists for this user");
        }

        applyDefaults(dto);
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
                .orElseThrow(() -> new ResourceNotFoundException("Product not found for this id: " + id ));
    }

    public ProductResponseDTO getDTOById(String id) {
        return productMapper.toResponseDTO(getById(id));
    }

    public ProductResponseDTO update(String id, UpdateProductDTO updatedProductData) {
        Product existingProduct = getById(id);

        if(!existingProduct.getName().equals(updatedProductData.getName()) &&
           productRepository.existsByNameAndUserId(updatedProductData.getName(), existingProduct.getUserId())) {
            throw new IllegalArgumentException("Product with this name already exists for this user");
        }

        productMapper.updateEntity(existingProduct, updatedProductData);
        Product saved = productRepository.save(existingProduct);
        return productMapper.toResponseDTO(saved);
    }

    public void delete(String id) {
        productRepository.deleteById(id);
    }

    public void ensureProductBelongsToUser(String productId, String userId) {
        Product product = getById(productId);
        if (!product.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Product with id: " + productId + " doesn't belong to user with id: " + userId);
        }
    }
}
