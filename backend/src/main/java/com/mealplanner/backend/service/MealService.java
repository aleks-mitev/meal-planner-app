package com.mealplanner.backend.service;

import com.mealplanner.backend.dto.CreateMealDTO;
import com.mealplanner.backend.dto.MealResponseDTO;
import com.mealplanner.backend.dto.UpdateMealDTO;
import com.mealplanner.backend.exception.ResourceNotFoundException;
import com.mealplanner.backend.mapper.MealMapper;
import com.mealplanner.backend.model.Meal;
import com.mealplanner.backend.model.Product;
import com.mealplanner.backend.repository.MealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MealService {

    private final MealRepository mealRepository;
    private final MealMapper mealMapper;
    private final UserService userService;
    private final ProductService productService;


    public MealResponseDTO create(CreateMealDTO dto) {
        userService.validateUserExists(dto.getUserId());

        Set<String> productIds = dto.getProducts().keySet();
        for (String productId : productIds) {
            productService.ensureProductBelongsToUser(productId, dto.getUserId());
        }
        if (mealRepository.existsByNameAndUserId(dto.getName(), dto.getUserId())) {
            throw new IllegalArgumentException("Meal with this name already exists for this user");
        }

        Meal meal = mealMapper.toEntity(dto);
        recalculateNutritionalValues(meal);
        Meal saved = mealRepository.save(meal);
        return mealMapper.toResponseDTO(saved);
    }

    public Meal getById(String id) {
        return mealRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Meal with id: " + id + " doesn't exist"));
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
            productService.ensureProductBelongsToUser(productId, existingMeal.getUserId());
        }

        if(!existingMeal.getName().equals(updatedMealData.getName()) &&
            mealRepository.existsByNameAndUserId(updatedMealData.getName(), existingMeal.getUserId())) {
                throw new IllegalArgumentException("Meal with this name already exists for this user");
        }

        mealMapper.updateEntity(existingMeal, updatedMealData);
        recalculateNutritionalValues(existingMeal);
        Meal saved = mealRepository.save(existingMeal);
        return mealMapper.toResponseDTO(saved);
    }

    public void delete(String id) {
        mealRepository.deleteById(id);
    }

    public void ensureMealBelongsToUser(String mealId, String userId) {
        Meal meal = getById(mealId);
        if (!meal.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Meal with id: " + mealId + " doesn't belong to user with id: " + userId);
        }
    }

    private void recalculateNutritionalValues(Meal meal) {
        double totalCalories = 0.0;
        double totalProtein = 0.0;
        double totalFat = 0.0;
        double totalCarbs = 0.0;
        double totalPrice = 0.0;

        for (Map.Entry<String, Double> entry : meal.getProducts().entrySet() ) {
            String productId = entry.getKey();
            Double quantityInGrams = entry.getValue();

            Product product = productService.getById(productId);
            double scale = quantityInGrams / 100.0;

            totalCalories += product.getCaloriesPer100g() * scale;
            totalProtein += product.getProteinPer100g() * scale;
            totalFat += product.getFatPer100g() * scale;
            totalCarbs += product.getCarbsPer100g() * scale;
            if (product.getPackageWeightGrams() != 0.0) {
                totalPrice += (product.getPackagePrice() / product.getPackageWeightGrams()) * quantityInGrams;
            }
        }

        meal.setCalories(totalCalories);
        meal.setProtein(totalProtein);
        meal.setFat(totalFat);
        meal.setCarbs(totalCarbs);
        meal.setPrice(totalPrice);
    }
}
