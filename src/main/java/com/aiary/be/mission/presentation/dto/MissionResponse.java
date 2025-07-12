package com.aiary.be.mission.presentation.dto;

import com.aiary.be.mission.application.dto.MissionInfo;

public record MissionResponse(
    String content
) {
    public static MissionResponse from(MissionInfo missionInfo) {
        return new MissionResponse(missionInfo.content());
    }
}
