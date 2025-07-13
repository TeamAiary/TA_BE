package com.aiary.be.diary.presentation;

import com.aiary.be.diary.application.DiaryFacade;
import com.aiary.be.diary.application.DiaryService;
import com.aiary.be.diary.presentation.dto.DiaryRequest;
import com.aiary.be.diary.presentation.dto.DiaryResponse;
import com.aiary.be.global.response.Message;
import com.aiary.be.global.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/diary")
public class DiaryController {
    private final DiaryService diaryService;
    private final DiaryFacade diaryFacade;
    
    // 월별 다이어리 불러오기
    @GetMapping
    public ResponseEntity<?> readAllDiary(
        @RequestParam int year,
        @RequestParam int month,
        @PageableDefault(page = 0, size = 10) Pageable pageable,
        @RequestAttribute Long userId
    ) {
        LocalDateTime[] range = DateUtil.searchRange(year, month);
        Page<DiaryResponse.Simple> responses = diaryService.readDiaryInfos(userId, range, pageable)
                                                 .map(DiaryResponse.Simple::from);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
    
    // 한 다이어리 내용 불러오기
    @GetMapping("/{diaryId}")
    public ResponseEntity<?> readOneDiary(
        @PathVariable Long diaryId,
        @RequestAttribute Long userId
    ) {
        DiaryResponse.Detail response = DiaryResponse.Detail.from(diaryFacade.readDiaryInfo(userId, diaryId));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    // 이번 주의 다이어리 작성 여부 불러오기
    @GetMapping("/weekly")
    public ResponseEntity<?> readWeeklyDiary(
        @RequestAttribute Long userId
    ) {
        DiaryResponse.WeeklyDo response = DiaryResponse.WeeklyDo.from(diaryFacade.weeklyWriteDiary(userId));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    // 다이어리 작성하기
    @PostMapping
    public ResponseEntity<?> createOneDiary(
        @RequestBody DiaryRequest diaryRequest,
        @RequestAttribute Long userId
    ) {
        diaryFacade.createDiary(userId, diaryRequest);
        return new ResponseEntity<>(
            Message.from("다이어리 생성에 성공했습니다."),
            HttpStatus.CREATED
        );
    }
    
    // 다이어리 삭제하기 (자신의 다이어리만 삭제 가능)
    @DeleteMapping("/{diaryId}")
    public ResponseEntity<?> deleteOneDiary(
        @PathVariable Long diaryId,
        @RequestAttribute Long userId
    ) {
        diaryFacade.deleteDiary(userId, diaryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
