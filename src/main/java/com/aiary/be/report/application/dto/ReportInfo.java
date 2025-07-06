package com.aiary.be.report.application.dto;

import com.aiary.be.global.util.DateUtil;
import com.aiary.be.report.domain.Report;

public record ReportInfo(
    String title,
    String content,
    String type,
    String startDate,
    String endDate,
    int depression,
    int anger,
    int happy,
    int riskScore
) {
    public static ReportInfo from(Report report) {
        return new ReportInfo(
            report.getTitle(), report.getContent(),
            report.getReportType().name(),
            DateUtil.dateFormating(report.getStartDate()),
            DateUtil.dateFormating(report.getEndDate()),
            report.getDepression(), report.getAnger(), report.getHappy(),
            report.getRiskScore()
        );
    }
}
