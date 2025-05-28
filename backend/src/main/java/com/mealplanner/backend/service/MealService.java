package com.mealplanner.backend.service;

import com.mealplanner.backend.dto.CreateMealDTO;
import com.mealplanner.backend.dto.MealResponseDTO;
import com.mealplanner.backend.dto.UpdateMealDTO;
import com.mealplanner.backend.exception.ResourceNotFoundException;
import com.mealplanner.backend.mapper.MealMapper;
import com.mealplanner.backend.model.Meal;
import com.mealplanner.backend.repository.MealRepository;
import com.mealplanner.backend.repository.ProductRepository;
import com.mealplanner.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MealService {

    private final MealRepository mealRepository;
    private final MealMapper mealMapper;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public MealResponseDTO create(CreateMealDTO dto) {
        if (!userRepository.existsById(dto.getUserId())) {
            throw new ResourceNotFoundException("User not found for userId: " + dto.getUserId());
        }
        Set<String> productIds = dto.getProducts().keySet();
        for (String productId : productIds) {
            if (!productRepository.existsByIdAndUserId(productId, dto.getUserId())) {
                throw new ResourceNotFoundException(
                        "Product with id: " + productId + " not found or doesn't belong to user: " + dto.getUserId()
                );
            }
        }
        if (mealRepository.existsByNameAndUserId(dto.getName(), dto.getUserId())) {
            throw new IllegalArgumentException("Meal with this name already exists for this user");
        }

        Meal meal = mealMapper.toEntity(dto);
        Meal saved = mealRepository.save(meal);
        return mealMapper.toResponseDTO(saved);
    }

    public Meal getById(String id) {
        return mealRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Meal not found"));
    }

    public MealResponseDTO getDTOById(String id) {
        return mealMapper.toResponseDTO(getById(id));
    }

    public List<MealResponseDTO> getAllByUser(String userId) {
        return mealRepository.findAllByUserId(userId)
                .stream()
                .map(mealMapper::toResponseDTO)
                .toList();
    }

    public MealResponseDTO update(String id, UpdateMealDTO updatedMealData) {
        Meal existingMeal = getById(id);

        Set<String> productIds = updatedMealData.getProducts().keySet();
        for (String productId : productIds) {
            if (!productRepository.existsByIdAndUserId(productId, existingMeal.getUserId())) {
                throw new ResourceNotFoundException(
                        "Product with id: " + productId +
                        " not found or doesn't belong to user: " + existingMeal.getUserId()
                );
            }
        }

        if(!existingMeal.getName().equals(updatedMealData.getName()) &&
            mealRepository.existsByNameAndUserId(updatedMealData.getName(), existingMeal.getUserId())) {
                throw new IllegalArgumentException("Meal with this name already exists for this user");
        }

        mealMapper.updateEntity(existingMeal, updatedMealData);
        Meal saved = mealRepository.save(existingMeal);
        return mealMapper.toResponseDTO(saved);
    }

    public void delete(String id) {
        mealRepository.deleteById(id);
    }
}
