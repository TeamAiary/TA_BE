package com.aiary.be.diary.presentation;

import com.aiary.be.diary.application.DiaryService;
import com.aiary.be.global.annotation.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/diary")
public class DiaryController {
    private final DiaryService diaryService;
    
    // Todo 자신의 모든 일기 조회 (페이지 네이션 가능성 有)
    @GetMapping
    public ResponseEntity<?> readAllDiary(
        @LoginUser Long userId
    ) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    // Todo 하나의 다이어리 읽기
    @GetMapping("/{diaryId}")
    public ResponseEntity<?> readOneDiary(
        @PathVariable Long diaryId,
        @LoginUser Long userId
    ) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    // Todo 다이어리 작성하기
    @PostMapping
    public ResponseEntity<?> createOneDiary(
        @LoginUser Long userId
    ) {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    // Todo 다이어리 수정하기 (자신의 다이어리만 수정 가능)
    @PatchMapping("/{diaryId}")
    public ResponseEntity<?> updateOneDiary(
        @PathVariable Long diaryId,
        @LoginUser Long userId
    ) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    // Todo 다이어리 삭제하기 (자신의 다이어리만 삭제 가능)
    @DeleteMapping("/{diaryId}")
    public ResponseEntity<?> deleteOneDiary(
        @PathVariable Long diaryId,
        @LoginUser Long userId
    ) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
