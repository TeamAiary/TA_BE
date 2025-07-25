package com.aiary.be.diary.application;

import com.aiary.be.diary.application.dto.DiaryInfo;
import com.aiary.be.diary.presentation.dto.DiaryRequest;
import com.aiary.be.global.annotation.Facade;
import com.aiary.be.global.exception.CustomException;
import com.aiary.be.global.exception.errorCode.DiaryErrorCode;
import com.aiary.be.global.util.DateUtil;
import com.aiary.be.mission.event.MissionEvent;
import com.aiary.be.user.application.UserService;
import com.aiary.be.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Facade
@RequiredArgsConstructor
public class DiaryFacade {
    private final ApplicationEventPublisher eventPublisher;
    private final DiaryService diaryService;
    private final UserService userService;
    
    public DiaryInfo readDiaryInfo(Long userId, Long diaryId) {
        if(!diaryService.userMatch(userId, diaryId)) {
            throw CustomException.from(DiaryErrorCode.NOT_MATCH);
        }
        
        return diaryService.readDiaryInfo(diaryId);
    }
    
    public DiaryInfo readTodayDiaryInfo(Long userId) {
        return diaryService.readTodayDiaryInfo(userId);
    }
    
    public List<Boolean> weeklyWriteDiary(Long userId) {
        LocalDateTime[] range = DateUtil.weeklyDiaryRange();
        
        List<Integer> writeDay = diaryService.readDiaryInfos(userId, range).stream()
                                     .map(diaryInfo -> diaryInfo.createdAt().getDayOfWeek().getValue())
                                     .toList();
        List<Boolean> isWrites = new ArrayList<>(List.of(false, false, false, false, false, false, false));
        for (Integer i : writeDay) {
            isWrites.set(i-1, true);
        }
        
        return isWrites;
    }
    
    public void createDiary(Long userId, DiaryRequest diaryRequest) {
        User user = userService.getUserForDiary(userId);
        
        diaryService.upsertDiary(user, diaryRequest);
        
        List<Boolean> booleans = weeklyWriteDiary(userId);
        long count = booleans.stream()
                        .filter(Boolean::booleanValue)
                        .count();
        
        eventPublisher.publishEvent(new MissionEvent(userId, count));
    }
    
    public void deleteDiary(Long userId, Long diaryId) {
        if(!diaryService.userMatch(userId, diaryId)) {
            throw CustomException.from(DiaryErrorCode.NOT_MATCH);
        }
        
        diaryService.deleteDiary(diaryId);
    }
}
