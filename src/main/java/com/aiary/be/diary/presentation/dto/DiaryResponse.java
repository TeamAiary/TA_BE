package com.aiary.be.diary.presentation.dto;

import com.aiary.be.diary.application.dto.DiaryInfo;

import java.time.LocalDateTime;
import java.util.List;

public class DiaryResponse {
    // 간략한 정보 (복수 조회)
    public record Simple(
        Long diaryId,
        String title,
        LocalDateTime createdAt,
        String weather,
        String preview,
        String emotion,
        int emotionPoint
    ) {
        public static Simple from(DiaryInfo diaryInfo) {
            return new Simple(
                diaryInfo.id(),
                diaryInfo.title(),
                diaryInfo.createdAt(),
                diaryInfo.weather().name(),
                diaryInfo.preview(),
                diaryInfo.emotion(),
                diaryInfo.emotionPoint()
            );
        }
    }
    
    // 자세한 정보 (단일 조회)
    public record Detail(
        String title,
        String content,
        LocalDateTime createdAt,
        String weather,
        int depression,
        int anger,
        int happy
    ) {
        public static Detail from(DiaryInfo diaryInfo) {
            return new Detail(
                diaryInfo.title(),
                diaryInfo.content(),
                diaryInfo.createdAt(),
                diaryInfo.weather().name(),
                diaryInfo.depression(),
                diaryInfo.anger(),
                diaryInfo.happy()
            );
        }
    }
    
    // 주간 다이어리 작성 여부
    public record WeeklyDo(List<Boolean> weeklyDo) {
        public static WeeklyDo from(List<Boolean> weeklyDo) {
            return new WeeklyDo(weeklyDo);
        }
    }
}
