package com.aiary.be.report.application;

import com.aiary.be.report.application.dto.ReportInfo;
import com.aiary.be.report.domain.Report;
import com.aiary.be.report.domain.ReportType;
import com.aiary.be.report.persistent.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    
    @Transactional(readOnly = true)
    public Page<ReportInfo> getReportInfo(Long userId, String type, Pageable pageable) {
        Page<Report> reports = reportRepository.findByUserIdAndReportTypeOrderByStartDateDesc(
            userId, ReportType.nameToEntity(type.toUpperCase()), pageable
        );
        return reports.map(ReportInfo::from);
    }
    
    @Transactional
    public void saveReportBulk(List<Report> reports) {
        reportRepository.saveAll(reports);
    }
}
