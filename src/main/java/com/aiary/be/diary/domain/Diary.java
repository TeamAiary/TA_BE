package com.aiary.be.diary.domain;

import com.aiary.be.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    private int depression;
    
    @Column
    private int anger;
    
    @Column
    private int happy;
    
    public Diary(
        User user,
        String title, String content,
        LocalDateTime createdAt, LocalDateTime modifiedAt,
        int depression, int anger, int happy
    ) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.depression = depression;
        this.anger = anger;
        this.happy = happy;
    }
    
    public String getPreview() {
        return content.substring(0, 12);
    }
    
    public void update(
        String title, String content, int depression, int anger, int happy
    ) {
        this.title = title;
        this.content = content;
        this.depression = depression;
        this.anger = anger;
        this.happy = happy;
    }
}
