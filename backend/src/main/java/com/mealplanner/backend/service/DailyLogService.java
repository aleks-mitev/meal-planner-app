package com.mealplanner.backend.service;

import com.mealplanner.backend.dto.CreateDailyLogDTO;
import com.mealplanner.backend.dto.DailyLogResponseDTO;
import com.mealplanner.backend.dto.UpdateDailyLogDTO;
import com.mealplanner.backend.exception.ResourceNotFoundException;
import com.mealplanner.backend.mapper.DailyLogMapper;
import com.mealplanner.backend.model.DailyLog;
import com.mealplanner.backend.repository.DailyLogRepository;
import com.mealplanner.backend.repository.MealRepository;
import com.mealplanner.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DailyLogService {
    private final DailyLogRepository dailyLogRepository;
    private final DailyLogMapper dailyLogMapper;
    private final UserRepository userRepository;
    private final MealRepository mealRepository;

    private void validateMealsExistAndBelongToUser(List<String> mealIds, String userId) {
        if (mealIds == null || mealIds.isEmpty()) {
            return;
        }

        for (String mealId : mealIds) {
            if (!mealRepository.existsByIdAndUserId(mealId, userId)) {
                throw new ResourceNotFoundException(
                    "Meal with id: " + mealId + " not found or doesn't belong to user: " + userId
                );
            }
        }
    }

    public DailyLogResponseDTO create(CreateDailyLogDTO dto) {
        if (!userRepository.existsById(dto.getUserId())) {
            throw new ResourceNotFoundException("User not found for userId: " + dto.getUserId());
        }

        LocalDate today = LocalDate.now();
        if (dailyLogRepository.existsByUserIdAndDate(dto.getUserId(), today)) {
            throw new IllegalArgumentException("Daily log already exists for today");
        }

        validateMealsExistAndBelongToUser(dto.getSelectedMealIds(), dto.getUserId());

        DailyLog dailyLog = dailyLogMapper.toEntity(dto);
        dailyLog.setDate(today);
        DailyLog saved = dailyLogRepository.save(dailyLog);
        return dailyLogMapper.toResponseDTO(saved);
    }

    public DailyLogResponseDTO update(String id, UpdateDailyLogDTO updatedLogData) {
        DailyLog existingLog = getById(id);
        
        validateMealsExistAndBelongToUser(updatedLogData.getSelectedMealIds(), existingLog.getUserId());

        dailyLogMapper.updateEntity(existingLog, updatedLogData);
        DailyLog saved = dailyLogRepository.save(existingLog);
        return dailyLogMapper.toResponseDTO(saved);
    }

    public DailyLog getById(String id) {
        return dailyLogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Daily log not found"));
    }

    public DailyLogResponseDTO getDTOById(String id) {
        return dailyLogMapper.toResponseDTO(getById(id));
    }

    public void delete(String id) {
        dailyLogRepository.deleteById(id);
    }

    public List<DailyLogResponseDTO> getAllByUser(String userId) {
        return dailyLogRepository.findAllByUserId(userId)
                .stream()
                .map(dailyLogMapper::toResponseDTO)
                .toList();
    }

    public DailyLogResponseDTO getLogForUserAndDate(String userId, LocalDate date) {
        return dailyLogRepository.findByUserIdAndDate(userId, date)
                .map(dailyLogMapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Daily log not found for this date: " + date));
    }

    public List<DailyLogResponseDTO> getLast7DaysLogsForUser(String userId) {
        LocalDate today = LocalDate.now();
        return getDailyLogsBetweenDates(userId, today.minusDays(7), today.minusDays(1));
    }

    public List<DailyLogResponseDTO> getLast30DaysLogsForUser(String userId) {
        LocalDate today = LocalDate.now();
        return getDailyLogsBetweenDates(userId, today.minusDays(30), today.minusDays(1));
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

}