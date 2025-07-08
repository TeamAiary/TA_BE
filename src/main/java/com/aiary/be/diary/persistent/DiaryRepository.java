package com.aiary.be.diary.persistent;

import com.aiary.be.diary.domain.Diary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    @Query("SELECT e FROM Diary e WHERE e.createdAt >= :startDate AND e.createdAt < :endDate")
    Page<Diary> findAllByUserIdAndCreatedAtBetween(
        Long userId,
        @Param("startDate") LocalDateTime start,
        @Param("endDate") LocalDateTime end,
        Pageable pageable
    );
    
    @Query("SELECT e FROM Diary e WHERE e.createdAt >= :startDate AND e.createdAt < :endDate")
    List<Diary> findAllByUserIdAndCreatedAtBetween(
        Long userId, LocalDateTime start, LocalDateTime end
    );
    
    Optional<Diary> findByUserIdOrderByIdDesc(Long userId);
}
