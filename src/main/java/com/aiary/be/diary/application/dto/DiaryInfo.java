package com.aiary.be.diary.application.dto;

import com.aiary.be.diary.domain.Diary;

import java.time.LocalDateTime;

public record DiaryInfo(
    String title,
    String content,
    LocalDateTime createdAt,
    int depression,
    int anger,
    int happy,
    String preview
) {
    public static DiaryInfo from(Diary diary) {
        return new DiaryInfo(
            diary.getTitle(),
            diary.getContent(),
            diary.getCreatedAt(),
            diary.getDepression(),
            diary.getAnger(),
            diary.getHappy(),
            diary.getPreview()
        );
    }
}
