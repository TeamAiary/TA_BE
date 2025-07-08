package com.aiary.be.global.util;

import com.aiary.be.report.domain.ReportType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static LocalDateTime[] targetMonthRange(int year, int month) {
        LocalDateTime[] range = new LocalDateTime[2];
        range[0] = LocalDateTime.of(year, month, 1, 0, 0, 0);
        
        range[1] = LocalDateTime.of(year,month, getEndDayOfMonth(year, month), 23, 59, 59);
        return range;
    }
    
    public static int getEndDayOfMonth(int year, int month) {
        // 윤년 조건
        if(isLeapYear(year) && month == 2) {
            return 29;
        }
        
        return switch (month) {
            case 1, 3, 5, 7, 8, 10, 12 -> 31;
            case 4, 6, 9, 11 -> 30;
            default -> 28;
        };
    }
    
    private static boolean isLeapYear(int year) {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }
    
    public static String dateFormating(LocalDate date) {
        return DateTimeFormatter.ISO_LOCAL_DATE.format(date);
    }
    
    public static LocalDateTime[] reportRange(ReportType reportType) {
        LocalDateTime[] range = new LocalDateTime[2];
        range[1] = LocalDateTime.now().minusMinutes(5);
        range[0] = range[1].minusDays(6);
        
        return range;
    }
    
    public static int latestMonthEndDay() {
        int month = LocalDate.now().getMonthValue();
        int year = LocalDate.now().getYear();
        
        if(month == 1) {
            return getEndDayOfMonth(year - 1, 12);
        }
        
        return getEndDayOfMonth(year, month - 1);
    }
}
