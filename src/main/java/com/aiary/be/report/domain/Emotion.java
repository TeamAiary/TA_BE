package com.aiary.be.report.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Embeddable
@Getter
@NoArgsConstructor
public class Emotion {
    private int depression;
    private int anger;
    private int happy;
    private int riskScore;
    
    public void calculateEmotion(
        List<Integer> depressions, List<Integer> angers, List<Integer> happies
    ) {
        calculateDepression(depressions);
        calculateAnger(angers);
        calculateHappy(happies);
        calculateRiskScore(depressions, angers, happies);
    }
    
    private void calculateRiskScore(List<Integer> depressions, List<Integer> angers, List<Integer> happies) {
        List<Integer> depressionReverseList = new ArrayList<>(depressions);
        List<Integer> angerReverseList = new ArrayList<>(angers);
        List<Integer> happyReverseList = new ArrayList<>(happies);
        
        Collections.reverse(depressionReverseList);
        Collections.reverse(angerReverseList);
        Collections.reverse(happyReverseList);
        
        double weight = 1.0;
        double riskScore = 0.0;
        for (int i = 0; i < depressions.size(); i++) {
            double value = (depressionReverseList.get(i) + angerReverseList.get(i) - happyReverseList.get(i) * 1.3) * weight;
            riskScore += value;
            weight -= 0.1;
        }
        riskScore /= depressions.size();
        
        this.riskScore = (int) riskScore;
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
