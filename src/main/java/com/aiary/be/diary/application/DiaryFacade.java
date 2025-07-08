package com.aiary.be.diary.application;

import com.aiary.be.diary.application.dto.DiaryInfo;
import com.aiary.be.diary.presentation.dto.DiaryRequest;
import com.aiary.be.global.annotation.Facade;
import com.aiary.be.global.exception.CustomException;
import com.aiary.be.global.exception.errorCode.DiaryErrorCode;
import com.aiary.be.user.application.UserService;
import com.aiary.be.user.domain.User;
import lombok.RequiredArgsConstructor;

@Facade
@RequiredArgsConstructor
public class DiaryFacade {
    private final DiaryService diaryService;
    private final UserService userService;
    
    public DiaryInfo readDiaryInfo(Long userId, Long diaryId) {
        if(!diaryService.userMatch(userId, diaryId)) {
            throw CustomException.from(DiaryErrorCode.NOT_MATCH);
        }
        
        return diaryService.readDiaryInfo(diaryId);
    }
    
    public void createDiary(Long userId, DiaryRequest diaryRequest) {
        User user = userService.getUserForDiary(userId);
        
        diaryService.createDiary(user, diaryRequest);
    }
    
    public void updateDiary(Long userId, Long diaryId, DiaryRequest diaryRequest) {
        if(!diaryService.userMatch(userId, diaryId)) {
            throw CustomException.from(DiaryErrorCode.NOT_MATCH);
        }
        
        diaryService.updateDiary(diaryId, diaryRequest);
    }
    
    public void deleteDiary(Long userId, Long diaryId) {
        if(!diaryService.userMatch(userId, diaryId)) {
            throw CustomException.from(DiaryErrorCode.NOT_MATCH);
        }
        
        diaryService.deleteDiary(diaryId);
    }
}
