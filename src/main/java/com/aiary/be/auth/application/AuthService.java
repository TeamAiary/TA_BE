package com.aiary.be.auth.application;


import com.aiary.be.auth.presentation.dto.LoginRequest;
import com.aiary.be.user.domain.User;
import com.aiary.be.user.persistent.UserRepository;
import com.aiary.be.auth.presentation.dto.SignupRequest;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    // 회원가입: 신규 유저 등록
    public void save(SignupRequest request) {
        User newUser = User.builder()
            .email(request.email())
            .password(request.password())
            .userName(request.userName())
            .age(request.age())
            .role(request.role())
            .gender(request.gender())
            .phoneNumber(request.phoneNumber())
            .build();

        userRepository.save(newUser);
    }
}
