package com.aiary.be.global.util;

import java.time.LocalDateTime;

public class DateUtil {
    public static LocalDateTime[] targetMonthRange(int year, int month) {
        LocalDateTime[] range = new LocalDateTime[2];
        range[0] = LocalDateTime.of(year, month, 1, 0, 0, 0);
        
        // 윤년 조건
        if(isLeapYear(year) && month == 2) {
            range[1] = LocalDateTime.of(year, month, 29, 23, 59, 59);
            return range;
        }
        
        LocalDateTime end = switch (month) {
            case 1, 3, 5, 7, 8, 10, 12 -> LocalDateTime.of(year, month, 31, 23, 59, 59);
            case 4, 6, 9, 11 -> LocalDateTime.of(year, month, 30, 23, 59, 59);
            default -> LocalDateTime.of(year, month, 28, 23, 59, 59);
        };
        
        range[1] = end;
        return range;
    }
    
    private static boolean isLeapYear(int year) {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }
}
