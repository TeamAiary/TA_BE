package com.aiary.be.report.presentation.dto;

import com.aiary.be.report.application.dto.ReportInfo;

public record ReportResponse(
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
    public static ReportResponse from(ReportInfo reportInfo) {
        return new ReportResponse(
            reportInfo.title(),
            reportInfo.content(),
            reportInfo.type(),
            reportInfo.startDate(),
            reportInfo.endDate(),
            reportInfo.depression(),
            reportInfo.anger(),
            reportInfo.happy(),
            reportInfo.riskScore()
        );
    }
}
