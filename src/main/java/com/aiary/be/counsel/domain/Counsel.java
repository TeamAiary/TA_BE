package com.aiary.be.counsel.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


// 사실 상담소 정보를 db에 저장은 따로 안 함. 사용하지 않는 entity인데 혹시 몰라서 일단 만들어놓기만 했어요.
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Counsel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Double latitude;

    @Column
    private Double longitude;

    @Column
    private String name;

    @Column
    private String phoneNumber;
}
