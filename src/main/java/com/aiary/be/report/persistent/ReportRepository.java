package com.aiary.be.report.persistent;

import com.aiary.be.report.domain.Report;
import com.aiary.be.report.domain.ReportType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReportRepository extends JpaRepository<Report, Long> {
    Page<Report> findByUserIdAndReportTypeOrderByStartDateDesc(
        Long userId, ReportType reportType, Pageable pageable
    );
    
    @Modifying
    @Query("DELETE FROM Report e WHERE e.user.id = :userId")
    void deleteReportsByUserId(
        @Param("userId") Long userId
    );
}
