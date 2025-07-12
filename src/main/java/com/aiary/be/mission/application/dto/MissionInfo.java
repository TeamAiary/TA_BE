package com.aiary.be.mission.application.dto;

import com.aiary.be.mission.domain.Mission;

public record MissionInfo(
    String content
) {
    public static MissionInfo from(Mission mission) {
        return new MissionInfo(mission.getContent());
    }
}
