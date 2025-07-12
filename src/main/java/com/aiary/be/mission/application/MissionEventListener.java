package com.aiary.be.mission.application;

import com.aiary.be.mission.event.MissionEvent;
import com.aiary.be.user.application.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MissionEventListener {
    private static final int ESSENTIAL_MISSION_ONE = 1;
    private static final int ESSENTIAL_MISSION_TWO = 5;
    private static final int ESSENTIAL_MISSION_THREE = 7;
    
    private final UserService userService;
    
    @EventListener
    public void essentialMissionCheck(MissionEvent missionEvent) {
        int count = missionEvent.count().intValue();
        
        switch(count) {
            case ESSENTIAL_MISSION_ONE -> userService.missionClear(missionEvent.userId(), 1);
            case ESSENTIAL_MISSION_TWO -> userService.missionClear(missionEvent.userId(), 2);
            case ESSENTIAL_MISSION_THREE -> userService.missionClear(missionEvent.userId(), 3);
        }
        
        log.info("유저 " + missionEvent.userId() + "번의 필수 미션 1개 수행 완료");
    }
}
