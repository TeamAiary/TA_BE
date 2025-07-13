package com.aiary.be.admin.presentation;

import com.aiary.be.global.response.Message;
import com.aiary.be.mission.application.MissionFacade;
import com.aiary.be.mission.application.MissionService;
import com.aiary.be.mission.presentation.dto.MissionRequest;
import com.aiary.be.mission.presentation.dto.MissionResponse;
import com.aiary.be.report.application.ReportFacade;
import com.aiary.be.report.domain.ReportType;
import com.aiary.be.user.application.UserFacade;
import com.aiary.be.user.application.UserService;
import com.aiary.be.user.presentation.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class adminApiController {
    private final UserService userService;
    private final UserFacade userFacade;
    private final ReportFacade reportFacade;
    private final MissionService missionService;
    private final MissionFacade missionFacade;
    
    // 모든 유저의 정보를 반환
    @GetMapping("user")
    public ResponseEntity<?> readAllUser(
        @PageableDefault Pageable pageable
    ) {
        Page<UserResponse> response = userService.findAllUserInfo(pageable);
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    // 유저의 역할을 변경
    @PostMapping("user/{userId}/grant")
    public ResponseEntity<?> grantUser(
        @PathVariable Long userId
    ) {
        userService.grant(userId);
        
        return new ResponseEntity<>(Message.from("유저 역할이 변경되었습니다."), HttpStatus.OK);
    }
    
    // 유저를 삭제
    @DeleteMapping("user/{userId}")
    public ResponseEntity<?> deleteUser(
        @PathVariable Long userId
    ) {
        userFacade.deleteUser(userId);
        
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    // 리포트 강제 생성
    @PostMapping("/report/activate")
    public ResponseEntity<?> reportAiActivate(
        @RequestParam String reportType
    ) {
        reportFacade.createReport(ReportType.nameToEntity(reportType.toUpperCase()));
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    // 미션을 추가
    @PostMapping("/mission")
    public ResponseEntity<?> addMission(
        @RequestBody MissionRequest missionRequest
    ) {
        missionService.makeMission(missionRequest);
        
        return new ResponseEntity<>(Message.from("미션이 생성되었습니다."), HttpStatus.CREATED);
    }
    
    // 미션 초기화
    @PostMapping("/mission/init")
    public ResponseEntity<?> forceInitMission() {
        missionFacade.initMission();
        
        return new ResponseEntity<>(Message.from("미션이 초기화 되었습니다"), HttpStatus.OK);
    }
    
    // 모든 미션을 반환
    @GetMapping("/mission/all")
    public ResponseEntity<?> getAllMissions(
        @PageableDefault Pageable pageable
    ) {
        Page<MissionResponse.Detail> response = missionService.getAllMission(pageable)
                                                    .map(MissionResponse.Detail::from);
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    // 미션 삭제
    @DeleteMapping("/{missionId}")
    public ResponseEntity<?> deleteMission(
        @PathVariable Long missionId
    ) {
        missionService.deleteMission(missionId);
        
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
