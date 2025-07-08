package com.aiary.be.report.persistent;

import com.aiary.be.report.domain.Report;
import com.aiary.be.report.domain.ReportType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
    Page<Report> findByUserIdAndReportTypeOrderByStartDateDesc(
        Long userId, ReportType reportType, Pageable pageable
    );
}
