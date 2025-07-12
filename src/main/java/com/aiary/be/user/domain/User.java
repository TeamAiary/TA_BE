package com.aiary.be.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    @Column
    private String password;

    @Column
    private String userName;

    @Column
    private int age;

    @Column
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column
    private String phoneNumber;
    
    @Column
    private int weeklyMission;

    public User(String email, String password, String userName, int age, Role role, Gender gender,
        String phoneNumber, PasswordEncoder passwordEncoder) {
        this.email = email;
        this.password = passwordEncoder.encode(password);
        this.userName = userName;
        this.age = age;
        this.role = role;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.weeklyMission = 0;
    }

    public boolean passwordMatches(String rawPassword, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(rawPassword, this.password);
    }

    public void update(String email, String rawPassword, String userName, int age, Gender gender,
        String phoneNumber, PasswordEncoder passwordEncoder) {
        this.email = email!=null?email:this.email;
        this.password = rawPassword!=null?passwordEncoder.encode(rawPassword):this.password;
        this.userName = userName!=null?userName:this.userName;
        this.age = age!=0?age:this.age;
        this.gender = gender!=null?gender:this.gender;
        this.phoneNumber = phoneNumber!=null?phoneNumber:this.phoneNumber;
    }
    
    public void resetWeeklyMission() {
        this.weeklyMission = 0;
    }
    
    public List<Boolean> getWeeklyMissionBool() {
        ArrayList<Boolean> response = new ArrayList<>();
        
        for (int i = 0; i < 6; i++) {
            // (mask >> i) & 1이 1이면 true, 아니면 false
            response.add(((weeklyMission >> i) & 1) == 1);
        }
        
        return response;
    }
}
