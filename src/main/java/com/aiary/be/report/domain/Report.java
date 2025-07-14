package com.aiary.be.report.domain;

import com.aiary.be.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    @Column
    private String title;
    
    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;
    
    @Column
    @Enumerated(EnumType.STRING)
    private ReportType reportType;
    
    @Column
    private LocalDate startDate;
    
    @Column
    private LocalDate endDate;
    
    @Embedded
    private Emotion emotion;
    
    public Report(
        User user,
        String title, String content,
        ReportType reportType,
        LocalDate startDate, LocalDate endDate,
        Emotion emotion
    ) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.reportType = reportType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.emotion = emotion;
    }
}
