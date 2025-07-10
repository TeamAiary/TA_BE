package com.aiary.be.user.presentation.dto;

import com.aiary.be.user.domain.Gender;

// todo: valid 추가

public record UserRequest(
    String email,
    String password,
    String userName,
    int age,
    Gender gender,
    String phoneNumber
) {}
