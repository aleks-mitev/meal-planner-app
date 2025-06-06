package com.mealplanner.backend.mapper;

import com.mealplanner.backend.dto.CreateProductDTO;
import com.mealplanner.backend.dto.ProductResponseDTO;
import com.mealplanner.backend.dto.UpdateProductDTO;
import com.mealplanner.backend.model.Product;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toEntity(CreateProductDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget Product product, UpdateProductDTO dto);

    ProductResponseDTO toResponseDTO(Product product);
}
