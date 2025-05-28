package com.mealplanner.backend.mapper;


import com.mealplanner.backend.dto.CreateDailyLogDTO;
import com.mealplanner.backend.dto.DailyLogResponseDTO;
import com.mealplanner.backend.dto.UpdateDailyLogDTO;
import com.mealplanner.backend.model.DailyLog;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DailyLogMapper {

    DailyLog toEntity(CreateDailyLogDTO dto);

    DailyLogResponseDTO toResponseDTO(DailyLog entity);

    void updateEntity(@MappingTarget DailyLog dailyLog, UpdateDailyLogDTO dto);
}

