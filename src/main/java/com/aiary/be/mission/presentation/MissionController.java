package com.aiary.be.mission.presentation;

import com.aiary.be.global.response.Message;
import com.aiary.be.mission.application.MissionFacade;
import com.aiary.be.mission.application.MissionService;
import com.aiary.be.mission.presentation.dto.ClearNumber;
import com.aiary.be.mission.presentation.dto.MissionRequest;
import com.aiary.be.mission.presentation.dto.MissionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mission")
@RequiredArgsConstructor
public class MissionController {
    private final MissionService missionService;
    private final MissionFacade missionFacade;
    
    @GetMapping
    public ResponseEntity<?> getMissions() {
        List<MissionResponse> response = missionService.getWeeklyMission().stream()
                                             .map(MissionResponse::from).toList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<?> addMissions(
        @RequestBody MissionRequest missionRequest
    ) {
        missionService.makeMission(missionRequest);
        
        return new ResponseEntity<>(Message.from("미션이 생성되었습니다."), HttpStatus.CREATED);
    }
    
    @PostMapping("/shuffle")
    public ResponseEntity<?> shuffleMissions() {
        missionService.shuffleMission();
        
        return new ResponseEntity<>(Message.from("미션이 변경되었습니다."), HttpStatus.OK);
    }
    
    @PostMapping("/clear")
    public ResponseEntity<?> clearMission(
        @RequestBody ClearNumber clearNumber,
        @RequestAttribute("userId") Long userId
    ) {
        missionFacade.clearMission(userId, clearNumber.number());
        
        return new ResponseEntity<>(Message.from("미션" + clearNumber.number() + "이 해결되었습니다"), HttpStatus.OK);
    }
    
    @PostMapping("/init")
    public ResponseEntity<?> forceInitMission() {
        missionFacade.initMission();
        
        return new ResponseEntity<>(Message.from("미션이 초기화 되었습니다"), HttpStatus.OK);
    }
}
