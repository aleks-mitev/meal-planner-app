package com.mealplanner.backend.service;

import com.mealplanner.backend.dto.CreateDailyLogDTO;
import com.mealplanner.backend.dto.DailyLogResponseDTO;
import com.mealplanner.backend.dto.UpdateDailyLogDTO;
import com.mealplanner.backend.exception.ResourceNotFoundException;
import com.mealplanner.backend.mapper.DailyLogMapper;
import com.mealplanner.backend.model.DailyLog;
import com.mealplanner.backend.repository.DailyLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DailyLogService {
    private final DailyLogRepository dailyLogRepository;
    private final DailyLogMapper dailyLogMapper;
    private final UserService userService;
    private final MealService mealService;

    private DailyLog createDailyLog(String userId, LocalDate date) {
        userService.validateUserExists(userId);

        DailyLog log = DailyLog.builder()
                .userId(userId)
                .date(date)
                .selectedMealIds(new ArrayList<>())
                .totalCalories(0.0)
                .totalProtein(0.0)
                .totalFat(0.0)
                .totalCarbs(0.0)
                .totalPrice(0.0)
                .build();

        return dailyLogRepository.save(log);
    }

    public DailyLog getById(String id) {
        return dailyLogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Daily log not found for id: " + id));
    }

    public DailyLogResponseDTO getDTOById(String id) {
        return dailyLogMapper.toResponseDTO(getById(id));
    }

    public DailyLogResponseDTO getLogForUserAndDate(String userId, LocalDate date) {
        userService.validateUserExists(userId);

        DailyLog log = dailyLogRepository.findByUserIdAndDate(userId, date)
                .orElseGet(() -> createDailyLog(userId, date));

        return dailyLogMapper.toResponseDTO(log);
    }

    private void validateMealsExistAndBelongToUser(List<String> mealIds, String userId) {
        if (mealIds == null || mealIds.isEmpty()) {
            return;
        }
        for (String mealId : mealIds) {
            mealService.ensureMealBelongsToUser(mealId, userId);
        }
    }

    private DailyLog applyUpdate(DailyLog existingLog, UpdateDailyLogDTO dto) {
        validateMealsExistAndBelongToUser(dto.getSelectedMealIds(), existingLog.getUserId());
        dailyLogMapper.updateEntity(existingLog, dto);
        recalculateNutritionalValues(existingLog);
        return dailyLogRepository.save(existingLog);
    }

    public DailyLogResponseDTO updateLogById(String id, UpdateDailyLogDTO updatedLogData) {
        DailyLog existingLog = getById(id);
        return dailyLogMapper.toResponseDTO(applyUpdate(existingLog, updatedLogData));
    }

    public DailyLogResponseDTO updateLogByUserAndDate(String userId, LocalDate date, UpdateDailyLogDTO updatedLogData) {
        DailyLog existingLog = dailyLogRepository.findByUserIdAndDate(userId, date)
                .orElseThrow(() -> new ResourceNotFoundException("No log for this user/date"));

        return dailyLogMapper.toResponseDTO(applyUpdate(existingLog, updatedLogData));
    }

    public void deleteLogById(String id) {
        dailyLogRepository.deleteById(id);
    }

    public void deleteLogByUserAndDate(String userId, LocalDate date) {
        DailyLog log = dailyLogRepository.findByUserIdAndDate(userId, date)
                .orElseThrow(() -> new ResourceNotFoundException("Log not found for user and date"));

        dailyLogRepository.deleteById(log.getId());
    }

    public List<DailyLogResponseDTO> getAllByUser(String userId) {
        return dailyLogRepository.findAllByUserId(userId)
                .stream()
                .map(dailyLogMapper::toResponseDTO)
                .toList();
    }

    public List<DailyLogResponseDTO> getLast7DaysLogsForUser(String userId) {
        LocalDate today = LocalDate.now();
        return getDailyLogsBetweenDates(userId, today.minusDays(7), today);
    }

    public List<DailyLogResponseDTO> getLast30DaysLogsForUser(String userId) {
        LocalDate today = LocalDate.now();
        return getDailyLogsBetweenDates(userId, today.minusDays(30), today);
    }

    private List<DailyLogResponseDTO> getDailyLogsBetweenDates(String userId,
                                                               LocalDate startDate,
                                                               LocalDate endDate) {
        return dailyLogRepository
                .findByUserIdAndDateBetweenOrderByDateDesc(userId, startDate, endDate)
                .stream()
                .map(dailyLogMapper::toResponseDTO)
                .toList();
    }

    private void recalculateNutritionalValues(DailyLog log) {
        double totalCalories = 0.0;
        double totalProtein = 0.0;
        double totalFat = 0.0;
        double totalCarbs = 0.0;
        double totalPrice = 0.0;

        for (String mealId : log.getSelectedMealIds()) {
            totalCalories += mealService.getById(mealId).getCalories();
            totalProtein += mealService.getById(mealId).getProtein();
            totalFat += mealService.getById(mealId).getFat();
            totalCarbs += mealService.getById(mealId).getCarbs();
            totalPrice += mealService.getById(mealId).getPrice();
        }

        log.setTotalCalories(totalCalories);
        log.setTotalProtein(totalProtein);
        log.setTotalFat(totalFat);
        log.setTotalCarbs(totalCarbs);
        log.setTotalPrice(totalPrice);
    }
}