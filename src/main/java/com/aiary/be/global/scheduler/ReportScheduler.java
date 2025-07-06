package com.aiary.be.global.scheduler;

import com.aiary.be.global.annotation.Scheduler;
import com.aiary.be.report.application.ReportFacade;
import com.aiary.be.report.domain.ReportType;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

@Scheduler
@RequiredArgsConstructor
public class ReportScheduler {
    private final ReportFacade reportFacade;
    
    @Scheduled(cron = "0 5 0 ? * MON")
    public void generateWeeklyReport() {
        reportFacade.createReport(ReportType.WEEKLY);
    }
    
    @Scheduled(cron = "0 5 0 1 * MON")
    public void generateMonthlyReport() {
        reportFacade.createReport(ReportType.MONTHLY);
    }
}
