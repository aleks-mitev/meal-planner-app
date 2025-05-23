package com.mealplanner.backend.repository;

import com.mealplanner.backend.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findAllByUserId(String userId);
    boolean existsByNameAndUserId(String name, String userId);
}
