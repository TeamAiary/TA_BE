package com.aiary.be.report.application;

import com.aiary.be.diary.application.DiaryService;
import com.aiary.be.diary.application.dto.DiaryInfo;
import com.aiary.be.global.annotation.Facade;
import com.aiary.be.global.util.DateUtil;
import com.aiary.be.report.domain.Report;
import com.aiary.be.report.domain.ReportType;
import com.aiary.be.user.application.UserService;
import com.aiary.be.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Facade
@RequiredArgsConstructor
public class ReportFacade {
    private final ReportService reportService;
    private final UserService userService;
    private final DiaryService diaryService;
    private final ReportClient reportClient;
    
    public void createReport(ReportType reportType) {
        // 전체 유저 조회
        List<User> users = userService.getAllUser();
        
        // 유저마다 리포트 작성
        int startLength = reportType.equals(ReportType.WEEKLY) ? 7 : DateUtil.latestMonthEndDay();
        for (User user : users) {
            List<DiaryInfo> diaryInfos = diaryService.readDiaryInfos(user.getId(), DateUtil.reportRange(reportType));
            String content = reportClient.analyze(diaryInfos);
            log.info(content);
            
            LocalDate start = LocalDate.now().minusDays(startLength);
            LocalDate end = LocalDate.now().minusDays(1);
            Report report = new Report(
                user,
                start + " ~ " + end,
                content,
                reportType,
                start,
                end
            );
            
            List<Integer> depressions = diaryInfos.stream().map(DiaryInfo::depression).toList();
            List<Integer> angers = diaryInfos.stream().map(DiaryInfo::anger).toList();
            List<Integer> happies = diaryInfos.stream().map(DiaryInfo::happy).toList();
            
            report.calculateDepression(depressions);
            report.calculateAnger(angers);
            report.calculateHappy(happies);
            
            reportService.createReport(report);
        }
    }
}
