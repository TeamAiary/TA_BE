package com.aiary.be.user.presentation;

import com.aiary.be.global.annotation.LoginUser;
import com.aiary.be.user.application.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    
    // Todo 자신의 프로필 읽기 기능
    @GetMapping
    public ResponseEntity<?> readMyProfile(
        @LoginUser Long userId
    ) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    // Todo 자신의 프로필 업데이트 기능
    @PatchMapping
    public ResponseEntity<?> updateMyProfile(
        @LoginUser Long userId
    ) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    // Todo 자신의 계정 삭제 기능
    @DeleteMapping
    public ResponseEntity<?> deleteMyProfile(
        @LoginUser Long userId
    ) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
