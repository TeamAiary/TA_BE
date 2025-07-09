package com.aiary.be.report.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Embeddable
@Getter
@NoArgsConstructor
public class Emotion {
    private int depression;
    private int anger;
    private int happy;
    
    public void calculateEmotion(
        List<Integer> depressions, List<Integer> angers, List<Integer> happies
    ) {
        calculateDepression(depressions);
        calculateAnger(angers);
        calculateHappy(happies);
    }
    
    private void calculateDepression(List<Integer> depressions) {
        double average = depressions.stream()
                             .mapToInt(Integer::intValue)
                             .average()
                             .orElse(0.0);
        
        this.depression = (int) average;
    }
    
    private void calculateAnger(List<Integer> angers) {
        double average = angers.stream()
                             .mapToInt(Integer::intValue)
                             .average()
                             .orElse(0.0);
        
        this.anger = (int) average;
    }
    
    private void calculateHappy(List<Integer> happies) {
        double average = happies.stream()
                             .mapToInt(Integer::intValue)
                             .average()
                             .orElse(0.0);
        
        this.happy = (int) average;
    }
}
