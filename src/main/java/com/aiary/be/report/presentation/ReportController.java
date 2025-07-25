package com.aiary.be.report.presentation;

import com.aiary.be.report.application.ReportFacade;
import com.aiary.be.report.application.ReportService;
import com.aiary.be.report.domain.ReportType;
import com.aiary.be.report.presentation.dto.ReportResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/report")
public class ReportController {
    private final ReportService reportService;
    private final ReportFacade reportFacade;
    
    // 리포트 가져오기
    @GetMapping("/{reportType}")
    public ResponseEntity<?> weeklyReport(
        @RequestAttribute Long userId,
        @PathVariable String reportType,
        @PageableDefault(page = 0, size = 10) Pageable pageable
    ) {
        Page<ReportResponse> response = reportService.getReportInfo(userId, reportType, pageable)
                                              .map(ReportResponse::from);
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
