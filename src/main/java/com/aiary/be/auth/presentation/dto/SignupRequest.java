package com.aiary.be.auth.presentation.dto;

import com.aiary.be.user.domain.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SignupRequest(
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    @NotBlank(message = "이메일은 필수값입니다.")
    String email,

    @NotBlank(message = "비밀번호는 필수값입니다.")
    String password,

    @NotBlank(message = "유저명은 필수값입니다.")
    String userName,

    int age,

    Gender gender,
    String phoneNumber
) {

}
