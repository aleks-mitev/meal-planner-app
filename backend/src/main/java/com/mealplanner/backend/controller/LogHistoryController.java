package com.mealplanner.backend.controller;

import com.mealplanner.backend.dto.DailyLogResponseDTO;
import com.mealplanner.backend.service.DailyLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v0/users/{userId}/logs")
@RequiredArgsConstructor
public class LogHistoryController {

    private final DailyLogService dailyLogService;

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

    @GetMapping("/date/{date}")
    public DailyLogResponseDTO getLogForUserAndDate(
            @PathVariable String userId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return dailyLogService.getLogForUserAndDate(userId, date);
    }
}
