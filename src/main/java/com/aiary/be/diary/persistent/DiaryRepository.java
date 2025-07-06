package com.aiary.be.diary.persistent;

import com.aiary.be.diary.domain.Diary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    Page<Diary> findAllByUserIdAndCreatedAtBetween(
        Long userId, LocalDateTime start, LocalDateTime end, Pageable pageable
    );
    
    List<Diary> findAllByUserIdAndCreatedAtBetween(
        Long userId, LocalDateTime start, LocalDateTime end
    );
    
    Optional<Diary> findByUserIdOrderByIdDesc(Long userId);
}
