package com.aiary.be.global.util;

import com.aiary.be.report.domain.ReportType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    // 오버로딩 : 대상 연, 월을 기준으로 1달의 기간을 반환
    public static LocalDateTime[] searchRange(int year, int month) {
        LocalDateTime[] range = new LocalDateTime[2];
        range[0] = LocalDateTime.of(year, month, 1, 0, 0, 0);
        range[1] = LocalDateTime.of(year, month + 1, 1, 0, 0, 0);
        return range;
    }
    
    // 오버로딩 : 리포트 타입을 기준으로 범위를 산정
    public static LocalDateTime[] searchRange(ReportType reportType) {
        LocalDateTime[] range = new LocalDateTime[2];
        range[1] = LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0));
        range[0] = reportType.equals(ReportType.WEEKLY) ? range[1].minusDays(7) : range[1].minusMonths(1);
        return range;
    }
    
    public static String dateFormating(LocalDate date) {
        return DateTimeFormatter.ISO_LOCAL_DATE.format(date);
    }
}
