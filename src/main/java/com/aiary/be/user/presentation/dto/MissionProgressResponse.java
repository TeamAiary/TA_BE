package com.aiary.be.user.presentation.dto;

import java.util.List;

public record MissionProgressResponse(
    List<Boolean> progress
) {
    public static MissionProgressResponse from(List<Boolean> progress) {
        return new MissionProgressResponse(progress);
    }
}
