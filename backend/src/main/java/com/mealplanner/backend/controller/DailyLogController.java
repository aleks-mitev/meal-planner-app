package com.mealplanner.backend.controller;

import com.mealplanner.backend.dto.CreateDailyLogDTO;
import com.mealplanner.backend.dto.DailyLogResponseDTO;
import com.mealplanner.backend.dto.UpdateDailyLogDTO;
import com.mealplanner.backend.service.DailyLogService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v0/users/{userId}/logs")
@RequiredArgsConstructor
public class DailyLogController {
    private final DailyLogService dailyLogService;

    @GetMapping("/{logId}")
    public DailyLogResponseDTO getDailyLogById(@PathVariable String logId) {
        return dailyLogService.getDTOById(logId);
    }

    @GetMapping("/date/{date}")
    public DailyLogResponseDTO getDailyLogForUserByDate(
            @PathVariable String userId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return dailyLogService.getLogForUserAndDate(userId, date);
    }

    @PutMapping("/{logId}")
    public DailyLogResponseDTO updateDailyLog(
            @PathVariable String logId,
            @RequestBody @Valid UpdateDailyLogDTO dto
    ) {
        return dailyLogService.updateLogById(logId, dto);
    }

    @PutMapping("/date/{date}")
    public DailyLogResponseDTO updateDailyLogForUserByDate(
            @PathVariable String userId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestBody @Valid UpdateDailyLogDTO dto
    ) {
        return dailyLogService.updateLogByUserAndDate(userId, date, dto);
    }

    @DeleteMapping("/{logId}")
    public void deleteDailyLog(@PathVariable String logId) {
        dailyLogService.deleteLogById(logId);
    }

    @DeleteMapping("/date/{date}")
    public void deleteDailyLogForUserByDate(
            @PathVariable String userId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        dailyLogService.deleteLogByUserAndDate(userId, date);
    }

    @GetMapping("/today")
    public DailyLogResponseDTO getTodayLogForUser(@PathVariable String userId) {
        LocalDate today = LocalDate.now();
        return dailyLogService.getLogForUserAndDate(userId, today);
    }

    @GetMapping("/yesterday")
    public DailyLogResponseDTO getYesterdayLogForUser(@PathVariable String userId) {
        LocalDate today = LocalDate.now();
        return dailyLogService.getLogForUserAndDate(userId, today.minusDays(1));
    }

    @GetMapping("/7-days")
    public List<DailyLogResponseDTO> getLast7DaysLogsForUser(@PathVariable String userId) {
        return dailyLogService.getLast7DaysLogsForUser(userId);
    }

    @GetMapping("/30-days")
    public List<DailyLogResponseDTO> getLast30DaysLogsForUser(@PathVariable String userId) {
        return dailyLogService.getLast30DaysLogsForUser(userId);
    }

    @GetMapping
    public List<DailyLogResponseDTO> getAllDailyLogsForUser(@PathVariable String userId) {
        return dailyLogService.getAllByUser(userId);
    }
}

