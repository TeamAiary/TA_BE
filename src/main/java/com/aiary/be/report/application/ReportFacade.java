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
import java.time.LocalDateTime;
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
        
        // Todo 로직 최적화해보기
        // 유저마다 리포트 작성 ( 매주 월요일 00:05 또는 매월 1일 00:05 실행 )
        for (User user : users) {
            LocalDateTime[] searchRange = DateUtil.searchRange(reportType);
            List<DiaryInfo> diaryInfos = diaryService.readDiaryInfos(user.getId(), searchRange);
            String content = reportClient.analyze(diaryInfos);
            
            LocalDate start = searchRange[0].toLocalDate();
            LocalDate end = searchRange[1].toLocalDate().minusDays(1);
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
