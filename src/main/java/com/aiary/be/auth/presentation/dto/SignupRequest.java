package com.aiary.be.auth.presentation.dto;

import com.aiary.be.user.domain.Gender;
import com.aiary.be.user.domain.Role;

public record SignupRequest(
    String email,
    String password,
    String userName,
    int age,
    Gender gender,
    String phoneNumber
) {}
