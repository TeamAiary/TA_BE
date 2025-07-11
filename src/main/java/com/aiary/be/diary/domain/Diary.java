package com.aiary.be.diary.domain;

import com.aiary.be.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Diary {
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
    private LocalDateTime createdAt;
    
    @Column
    private LocalDateTime modifiedAt;
    
    @Column
    @Enumerated(EnumType.STRING)
    private Weather weather;
    
    @Column
    private int depression;
    
    @Column
    private int anger;
    
    @Column
    private int happy;
    
    public Diary(
        User user,
        String title, String content,
        LocalDateTime createdAt, LocalDateTime modifiedAt,
        Weather weather,
        int depression, int anger, int happy
    ) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.weather = weather;
        this.depression = depression;
        this.anger = anger;
        this.happy = happy;
    }
    
    public String getPreview() {
        return content.substring(0, 12);
    }
    
    public int getMostEmotionPoint() {
        List<Integer> emotions = List.of(depression, anger, happy);
        return Collections.max(emotions);
    }
    
    public String getMostEmotion() {
        int maxValue = getMostEmotionPoint();
        
        if(maxValue == depression) {
            return "depression";
        }
        if(maxValue == anger) {
            return "anger";
        }
        
        return "happy";
        
        
    }
    
    public void update(
        String title, String content, Weather weather, int depression, int anger, int happy
    ) {
        this.title = title != null ? title : this.title;
        this.content = content != null ? content : this.content;
        this.weather = weather != null ? weather : this.weather;
        this.depression = depression != 0 ? depression : this.depression;
        this.anger = anger != 0 ? anger : this.anger;
        this.happy = happy != 0 ? happy : this.happy;
        
        this.modifiedAt = LocalDateTime.now();
    }
}
