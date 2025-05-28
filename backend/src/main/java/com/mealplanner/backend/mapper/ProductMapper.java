package com.mealplanner.backend.mapper;

import com.mealplanner.backend.dto.CreateProductDTO;
import com.mealplanner.backend.dto.ProductResponseDTO;
import com.mealplanner.backend.dto.UpdateProductDTO;
import com.mealplanner.backend.model.Product;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    Product toEntity(CreateProductDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    void updateEntity(@MappingTarget Product product, UpdateProductDTO dto);

    ProductResponseDTO toResponseDTO(Product product);
}
