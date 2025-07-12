package com.aiary.be.mission.application.dto;

import com.aiary.be.mission.domain.Mission;

public class MissionInfo {
    public record Simple(String content) {
        public static Simple from(Mission mission) {
            return new Simple(mission.getContent());
        }
    }
    
    public record Detail(Long id, String content, boolean activate) {
        public static Detail from(Mission mission) {
            return new Detail(mission.getId(), mission.getContent(), mission.isActivate());
        }
    }
}
