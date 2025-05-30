package com.mealplanner.backend.repository;

import com.mealplanner.backend.model.DailyLog;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DailyLogRepository extends MongoRepository<DailyLog, String> {
    List<DailyLog> findAllByUserId(String userId);
    Optional<DailyLog> findByUserIdAndDate(String userId, LocalDate date);
    boolean existsByUserIdAndDate(String userId, LocalDate date);
    List<DailyLog> findByUserIdAndDateBetweenOrderByDateDesc(String userId, LocalDate startDate, LocalDate endDate);
}