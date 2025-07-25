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
        
        // 유저-주간 다이어리 쌍
        Map<Long, List<DiaryInfo>> userIdDiary = users.stream()
                                                     .map(user -> Map.entry(user.getId(), diaryService.readDiaryInfos(user.getId(), searchRange)))
                                                     .filter(entry -> !entry.getValue().isEmpty())
                                                     .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        
        // 유저-주간 감정 쌍
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
        
        
        // 핵심 : 외부 api 호출을 non-blocking으로 하고, 이후에 응답을 모아서 한 번에 bulk로 저장
        Flux.fromIterable(userIdDiary.entrySet())
            .flatMap(entry -> reportClient.analyze(entry.getKey(), entry.getValue(), reportType))
            .collectList()
            .publishOn(Schedulers.boundedElastic())
            .flatMap((apiResponses) -> {
                List<Report> reports = new ArrayList<>();
                for (AiResponse apiResponse : apiResponses) {
                    reports.add(new Report(
                        userIdMap.get(apiResponse.userId()),
                        start + "부터 " + end + "까지의 나.",
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
