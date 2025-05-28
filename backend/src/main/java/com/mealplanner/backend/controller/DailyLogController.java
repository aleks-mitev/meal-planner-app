package com.mealplanner.backend.controller;

import com.mealplanner.backend.dto.CreateDailyLogDTO;
import com.mealplanner.backend.dto.DailyLogResponseDTO;
import com.mealplanner.backend.dto.UpdateDailyLogDTO;
import com.mealplanner.backend.service.DailyLogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v0/users/{userId}/logs")
@RequiredArgsConstructor
public class DailyLogController {
    private final DailyLogService dailyLogService;

    @PostMapping
    public DailyLogResponseDTO createDailyLog(@PathVariable String userId, @RequestBody @Valid CreateDailyLogDTO dto) {
        dto.setUserId(userId);
        return dailyLogService.create(dto);
    }

    @GetMapping
    public List<DailyLogResponseDTO> getAllDailyLogsForUser(@PathVariable String userId) {
        return dailyLogService.getAllByUser(userId);
    }

    @GetMapping("/{logId}")
    public DailyLogResponseDTO getDailyLogById(@PathVariable String logId) {
        return dailyLogService.getDTOById(logId);
    }

    @PutMapping("/{logId}")
    public DailyLogResponseDTO updateDailyLog(
            @PathVariable String logId,
            @RequestBody @Valid UpdateDailyLogDTO dto
    ) {
        return dailyLogService.update(logId, dto);
    }

    @DeleteMapping("/{logId}")
    public void deleteDailyLog(@PathVariable String logId) {
        dailyLogService.delete(logId);
    }
}

