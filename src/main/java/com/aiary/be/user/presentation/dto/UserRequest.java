package com.aiary.be.user.presentation.dto;

import com.aiary.be.user.domain.Gender;

// user 정보 update에만 사용되는 dto
// 나중에 login/signup request 등이랑 통합 예정
// 얘는 전달하지 않는 필드는 update하지 않기 때문에 valid를 추가하지 않았음
public record UserRequest(
    String email,
    String password,
    String userName,
    int age,
    Gender gender,
    String phoneNumber
) {}
