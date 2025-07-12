package com.aiary.be.global.scheduler;

import com.aiary.be.global.annotation.Scheduler;
import com.aiary.be.mission.application.MissionFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

@Scheduler
@RequiredArgsConstructor
public class MissionScheduler {
    private final MissionFacade missionFacade;
    
    @Scheduled(cron = "0 1 0 ? * MON")
    public void initMissions() {missionFacade.initMission();}
}
