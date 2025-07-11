package com.aiary.be.diary.application.dto;

import com.aiary.be.diary.domain.Diary;
import com.aiary.be.diary.domain.Weather;

import java.time.LocalDateTime;

public record DiaryInfo(
    String title,
    String content,
    LocalDateTime createdAt,
    Weather weather,
    int depression,
    int anger,
    int happy,
    String preview,
    String emotion,
    int emotionPoint
) {
    public static DiaryInfo from(Diary diary) {
        return new DiaryInfo(
            diary.getTitle(),
            diary.getContent(),
            diary.getCreatedAt(),
            diary.getWeather(),
            diary.getDepression(),
            diary.getAnger(),
            diary.getHappy(),
            diary.getPreview(),
            diary.getMostEmotion(),
            diary.getMostEmotionPoint()
        );
    }
}
