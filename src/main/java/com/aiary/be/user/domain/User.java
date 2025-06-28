package com.aiary.be.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column
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
}
