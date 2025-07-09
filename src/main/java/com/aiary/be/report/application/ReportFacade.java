package com.aiary.be.report.application;

import com.aiary.be.diary.application.DiaryService;
import com.aiary.be.diary.application.dto.DiaryInfo;
import com.aiary.be.global.annotation.Facade;
import com.aiary.be.global.util.DateUtil;
import com.aiary.be.report.application.dto.AiResponse;
import com.aiary.be.report.domain.Emotion;
import com.aiary.be.report.domain.Report;
import com.aiary.be.report.domain.ReportType;
import com.aiary.be.user.application.UserService;
import com.aiary.be.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        Map<Long, User> userIdMap = new HashMap<>();
        for (User user : users) {
            userIdMap.put(user.getId(), user);
        }
        
        // 범위 산정
        LocalDateTime[] searchRange = DateUtil.searchRange(reportType);
        LocalDate start = searchRange[0].toLocalDate();
        LocalDate end = searchRange[1].toLocalDate().minusDays(1);
        
        // 유저 - 주간 다이어리 쌍
        Map<Long, List<DiaryInfo>> userIdDiary = users.stream()
                                                     .collect(Collectors.toMap(
                                                         User::getId,
                                                         user -> diaryService.readDiaryInfos(user.getId(), searchRange)
                                                     ));
        // 유저 - 주간 감정 쌍
        Map<Long, Emotion> userIdEmotion = userIdDiary.keySet().stream()
                                          .collect(Collectors.toMap(
                                              userId -> userId,
                                              userId -> {
                                                  List<DiaryInfo> diaryInfos = userIdDiary.get(userId);
                                                  List<Integer> depressions = diaryInfos.stream().map(DiaryInfo::depression).toList();
                                                  List<Integer> angers = diaryInfos.stream().map(DiaryInfo::anger).toList();
                                                  List<Integer> happies = diaryInfos.stream().map(DiaryInfo::happy).toList();
                                                  Emotion emotion = new Emotion();
                                                  emotion.calculateEmotion(depressions, angers, happies);
                                                  return emotion;
                                              }
                                          ));
        
        
        Flux.fromIterable(userIdDiary.entrySet())
            .flatMap(entry -> reportClient.analyze(entry.getKey(), entry.getValue()))
            .collectList()
            .publishOn(Schedulers.boundedElastic())
            .publishOn(Schedulers.boundedElastic())
            .flatMap((apiResponses) -> {
                List<Report> reports = new ArrayList<>();
                for (AiResponse apiResponse : apiResponses) {
                    reports.add(new Report(
                        userIdMap.get(apiResponse.userId()),
                        start + " - " + end,
                        apiResponse.content(),
                        reportType,
                        start,
                        end,
                        userIdEmotion.get(apiResponse.userId())
                    ));
                }
                
                reportService.saveReportBulk(reports);
                return Mono.empty();
            })
            .subscribe();
    }
}
