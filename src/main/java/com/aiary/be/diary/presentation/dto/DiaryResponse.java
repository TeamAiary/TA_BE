package com.aiary.be.diary.presentation.dto;

import com.aiary.be.diary.application.dto.DiaryInfo;

import java.time.LocalDateTime;

public class DiaryResponse {
    public record Simple(
        String title,
        LocalDateTime createdAt,
        String preview
    ) {
        public static Simple from(DiaryInfo diaryInfo) {
            return new Simple(
                diaryInfo.title(),
                diaryInfo.createdAt(),
                diaryInfo.preview()
            );
        }
    }
    
    public record Detail(
        String title,
        String content,
        LocalDateTime createdAt,
        int depression,
        int anger,
        int happy
    ) {
        public static Detail from(DiaryInfo diaryInfo) {
            return new Detail(
                diaryInfo.title(),
                diaryInfo.content(),
                diaryInfo.createdAt(),
                diaryInfo.depression(),
                diaryInfo.anger(),
                diaryInfo.happy()
            );
        }
    }
}
