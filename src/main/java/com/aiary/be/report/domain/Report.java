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
    
    @Column
    private String content;
    
    @Column
    @Enumerated(EnumType.STRING)
    private ReportType reportType;
    
    @Column
    private LocalDate startDate;
    
    @Column
    private LocalDate endDate;
    
    @Column
    private int depression;
    
    @Column
    private int anger;
    
    @Column
    private int happy;
    
    @Column
    private int riskScore;
}
