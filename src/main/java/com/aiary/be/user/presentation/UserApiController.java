package com.aiary.be.user.presentation;

import com.aiary.be.global.response.Message;
import com.aiary.be.user.application.UserService;
import com.aiary.be.user.presentation.dto.MissionProgressResponse;
import com.aiary.be.user.presentation.dto.UserRequest;
import com.aiary.be.user.presentation.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserApiController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> readMyProfile(
        @RequestAttribute("userId") Long userId
    ) {
        UserResponse response = userService.getUserById(userId);
        return ResponseEntity.status(HttpStatus.OK)
            .body(response);
    }

    @PatchMapping
    public ResponseEntity<?> updateMyProfile(
        @RequestAttribute("userId") Long userId,
        @RequestBody UserRequest userRequest
    ) {
        userService.updateUser(userId, userRequest);

        return ResponseEntity.status(HttpStatus.OK)
            .body(Message.from("프로필 수정에 성공하였습니다."));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteMyProfile(
        @RequestAttribute("userId") Long userId
    ) {
        userService.deleteUser(userId);

        return ResponseEntity.status(HttpStatus.OK)
            .body(Message.from("계정 삭제에 성공하였습니다."));
    }
    
    @GetMapping("/mission")
    public ResponseEntity<?> getWeeklyMissionProgress(
        @RequestAttribute("userId") Long userId
    ) {
        MissionProgressResponse response = MissionProgressResponse.from(
            userService.getBooleanWeeklyMission(userId)
        );
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
