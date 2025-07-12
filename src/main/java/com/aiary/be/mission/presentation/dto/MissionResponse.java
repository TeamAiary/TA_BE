package com.aiary.be.mission.presentation.dto;

import com.aiary.be.mission.application.dto.MissionInfo;

public class MissionResponse {
    public record Simple(String content) {
        public static Simple from(MissionInfo.Simple simpleInfo) {
            return new Simple(simpleInfo.content());
        }
    }
    
    public record Detail(Long id, String content, boolean activate) {
        public static Detail from(MissionInfo.Detail detailInfo) {
            return new Detail(detailInfo.id(), detailInfo.content(), detailInfo.activate());
        }
    }
}
