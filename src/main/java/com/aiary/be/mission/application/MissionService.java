package com.aiary.be.mission.application;

import com.aiary.be.mission.application.dto.MissionInfo.*;
import com.aiary.be.mission.domain.Mission;
import com.aiary.be.mission.persistent.MissionRepository;
import com.aiary.be.mission.presentation.dto.MissionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MissionService {
    private final static int NON_ESSENTIAL_MISSION_NUMBER = 3;
    
    private final MissionRepository missionRepository;
    
    @Transactional(readOnly = true)
    public List<Simple> getWeeklyMission() {
        List<Mission> essentialMissions = missionRepository.findByEssentialIsTrue();
        essentialMissions.addAll(missionRepository.findByEssentialIsFalseAndActivateIsTrue());
        
        return essentialMissions.stream()
                   .map(Simple::from).toList();
    }
    
    @Transactional
    public void makeMission(MissionRequest missionRequest) {
        Mission mission = new Mission(missionRequest.content(), missionRequest.essential());
        
        missionRepository.save(mission);
    }
    
    @Transactional
    public void shuffleMission() {
        List<Mission> missions = missionRepository.findByEssentialIsFalse();
        for (Mission mission : missions) {
            mission.deactivate();
        }
        
        Collections.shuffle(missions);
        List<Mission> targets = missions.subList(0, NON_ESSENTIAL_MISSION_NUMBER);
        
        for (Mission target : targets) {
            target.activate();
        }
    }
    
    @Transactional(readOnly = true)
    public Page<Detail> getAllMission(Pageable pageable) {
        return missionRepository.findAllByEssentialFalse(pageable)
                   .map(Detail::from);
    }
    
    @Transactional
    public void deleteMission(Long missionId) {
        missionRepository.deleteById(missionId);
    }
}
