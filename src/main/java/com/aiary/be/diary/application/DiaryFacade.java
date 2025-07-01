package com.aiary.be.diary.application;

import com.aiary.be.diary.presentation.dto.DiaryRequest;
import com.aiary.be.global.annotation.Facade;
import com.aiary.be.user.application.UserService;
import com.aiary.be.user.domain.User;
import lombok.RequiredArgsConstructor;

@Facade
@RequiredArgsConstructor
public class DiaryFacade {
    private final DiaryService diaryService;
    private final UserService userService;
    
    public void createDiary(Long userId, DiaryRequest diaryRequest) {
        User user = userService.getUserForDiary(userId);
        
        diaryService.createDiary(user, diaryRequest);
    }
}
