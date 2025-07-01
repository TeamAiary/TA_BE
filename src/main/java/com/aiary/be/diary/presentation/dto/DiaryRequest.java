package com.aiary.be.diary.presentation.dto;

public record DiaryRequest(
    String title,
    String content,
    int depression,
    int anger,
    int happy
) {
}
