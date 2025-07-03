package com.aiary.be.diary.persistent;

import com.aiary.be.diary.domain.Diary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    Page<Diary> findAllByUserIdAndCreatedAtBetween(
        Long userId, LocalDateTime start, LocalDateTime end, Pageable pageable
    );
}
