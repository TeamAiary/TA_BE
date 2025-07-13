package com.aiary.be.global.util;

import com.aiary.be.report.domain.ReportType;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

public class DateUtil {
    // 오버로딩 : 대상 연, 월을 기준으로 1달의 기간을 반환
    public static LocalDateTime[] searchRange(int year, int month) {
        LocalDateTime[] range = new LocalDateTime[2];
        range[0] = LocalDateTime.of(year, month, 1, 0, 0, 0);
        
        int endYear = month != 12 ? year : year+1;
        int endMonth = month != 12 ? month+1 : 1;
        range[1] = LocalDateTime.of(endYear, endMonth, 1, 0, 0, 0);
        return range;
    }
    
    // 오버로딩 : 리포트 타입을 기준으로 범위를 산정
    public static LocalDateTime[] searchRange(ReportType reportType) {
        LocalDateTime[] range = new LocalDateTime[2];
        range[1] = LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0));
        range[0] = reportType.equals(ReportType.WEEKLY) ? range[1].minusDays(7) : range[1].minusMonths(1);
        return range;
    }
    
    // 주간 다이어리 작성 여부 확인을 위한 범위를 산정
    public static LocalDateTime[] weeklyDiaryRange() {
        LocalDateTime today = LocalDateTime.now();

        // 이번 주의 월요일 (시작)
        LocalDateTime startOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        // 이번 주의 일요일 (끝)
        LocalDateTime endOfWeek = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        
        LocalDateTime[] range = new LocalDateTime[2];
        range[0] = startOfWeek;
        range[1] = endOfWeek;
        
        return range;
    }
    
    public static String dateFormating(LocalDate date) {
        return DateTimeFormatter.ISO_LOCAL_DATE.format(date);
    }
}
