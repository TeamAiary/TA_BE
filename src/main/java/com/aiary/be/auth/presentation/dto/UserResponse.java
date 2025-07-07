package com.aiary.be.auth.presentation.dto;

import com.aiary.be.user.domain.Gender;
import com.aiary.be.user.domain.Role;
import com.aiary.be.user.domain.User;

public record UserResponse(
    Long userId,
    String email,
    String userName,
    int age,
    Gender gender,
    Role role,
    String phoneNumber
) {
    public static UserResponse from(User user){
        return new UserResponse(
            user.getId(),
            user.getEmail(),
            user.getUserName(),
            user.getAge(),
            user.getGender(),
            user.getRole(),
            user.getPhoneNumber()
        );
    }

}
