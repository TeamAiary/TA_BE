package com.aiary.be.mission.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Mission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column
    private String content;
    
    @Column
    private boolean essential;
    
    @Column
    private boolean activate;
    
    public Mission(String content, boolean essential) {
        this.content = content;
        this.essential = essential;
        this.activate = false;
    }
    
    public void activate() {
        this.activate = true;
    }
    
    public void deactivate() {
        this.activate = false;
    }
}
