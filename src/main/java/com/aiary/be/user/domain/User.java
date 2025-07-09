package com.aiary.be.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    public User(String email, String password, String userName, int age, Role role, Gender gender,
        String phoneNumber, PasswordEncoder passwordEncoder) {
        this.email = email;
        this.password = passwordEncoder.encode(password);
        this.userName = userName;
        this.age = age;
        this.role = role;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
    }

    public boolean passwordMatches(String rawPassword, PasswordEncoder passwordEncoder){
        return passwordEncoder.matches(rawPassword, this.password);
    }
}
