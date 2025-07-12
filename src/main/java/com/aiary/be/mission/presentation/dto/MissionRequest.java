package com.aiary.be.mission.presentation.dto;

public record MissionRequest(
    String content,
    boolean essential
) {
}
