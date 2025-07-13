package com.aiary.be.user.application;

import com.aiary.be.diary.application.DiaryService;
import com.aiary.be.global.annotation.Facade;
import com.aiary.be.report.application.ReportService;
import lombok.RequiredArgsConstructor;

@Facade
@RequiredArgsConstructor
public class UserFacade {
    private final DiaryService diaryService;
    private final ReportService reportService;
    private final UserService userService;
    
    public void deleteUser(Long userId) {
        diaryService.deleteByUserId(userId);
        reportService.deleteReportByUserId(userId);
        userService.deleteUser(userId);
    }
}
