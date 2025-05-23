package com.mealplanner.backend.mapper;

import com.mealplanner.backend.dto.CreateProductDTO;
import com.mealplanner.backend.dto.ProductResponseDTO;
import com.mealplanner.backend.dto.UpdateProductDTO;
import com.mealplanner.backend.model.Product;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    // Create a new Product from DTO
    @Mapping(target = "id", ignore = true)
    Product toEntity(CreateProductDTO dto);

    // Update existing Product from update DTO
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true) // don't allow userId change
    void updateEntity(@MappingTarget Product product, UpdateProductDTO dto);

    // Convert Product to response DTO
    ProductResponseDTO toResponseDTO(Product product);
}
