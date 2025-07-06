package com.aiary.be.report.domain;

import com.aiary.be.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

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
    
    @Column
    private int depression;
    
    @Column
    private int anger;
    
    @Column
    private int happy;
    
    @Column
    private int riskScore;
    
    public Report(User user, String title, String content, ReportType reportType, LocalDate startDate, LocalDate endDate) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.reportType = reportType;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    
    public void calculateDepression(List<Integer> depressions) {
        double average = depressions.stream()
                              .mapToInt(Integer::intValue)
                              .average()
                              .orElse(0.0);
        
        this.depression = (int) average;
    }
    
    public void calculateAnger(List<Integer> angers) {
        double average = angers.stream()
                             .mapToInt(Integer::intValue)
                             .average()
                             .orElse(0.0);
        
        this.anger = (int) average;
    }
    
    public void calculateHappy(List<Integer> happies) {
        double average = happies.stream()
                             .mapToInt(Integer::intValue)
                             .average()
                             .orElse(0.0);
        
        this.happy = (int) average;
    }
    
    public void calculateRisk(int riskScore) {
        this.riskScore = riskScore;
    }
}
