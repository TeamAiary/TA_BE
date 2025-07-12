package com.aiary.be.mission.application;

import com.aiary.be.global.annotation.Facade;
import com.aiary.be.user.application.UserService;
import com.aiary.be.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Facade
@RequiredArgsConstructor
public class MissionFacade {
    private final MissionService missionService;
    private final UserService userService;
    
    public void clearMission(Long userId, int number) {
        userService.missionClear(userId, number);
    }
    
    @Transactional
    public void initMission() {
        List<User> users = userService.getAllUser();
        for (User user : users) {
            user.resetWeeklyMission();
        }
        
        missionService.shuffleMission();
    }
}
