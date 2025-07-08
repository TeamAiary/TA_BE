package com.aiary.be.diary.presentation.dto;

public record DiaryRequest(
    String title,
    String content,
    String weather,
    int depression,
    int anger,
    int happy
) {
}
