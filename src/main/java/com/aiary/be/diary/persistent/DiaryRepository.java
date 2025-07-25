package com.aiary.be.diary.persistent;

import com.aiary.be.diary.domain.Diary;
import com.aiary.be.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    @Query("SELECT e FROM Diary e WHERE e.createdAt >= :startDate AND e.createdAt < :endDate  AND e.user.id = :userId")
    Page<Diary> findAllByUserIdAndCreatedAtBetween(
        @Param("userId") Long userId,
        @Param("startDate") LocalDateTime start,
        @Param("endDate") LocalDateTime end,
        Pageable pageable
    );
    
    @Query("SELECT e FROM Diary e WHERE e.createdAt >= :startDate AND e.createdAt < :endDate AND e.user.id = :userId")
    List<Diary> findAllByUserIdAndCreatedAtBetween(
        @Param("userId") Long userId,
        @Param("startDate") LocalDateTime start,
        @Param("endDate") LocalDateTime end
    );
    
    @Query("SELECT e from Diary e WHERE e.user.id = :userId ORDER BY e.id DESC LIMIT 1")
    Optional<Diary> findByUserIdOrderByIdDesc(
        @Param("userId") Long userId
    );
    
    @Modifying
    @Query("DELETE FROM Diary e WHERE e.user.id = :userId")
    void deleteDiariesByUserId(
        @Param("userId") Long userId
    );
    
    Long user(User user);
}
