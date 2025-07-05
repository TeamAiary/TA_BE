package com.aiary.be.auth.application;


import com.aiary.be.user.domain.User;
import com.aiary.be.user.persistent.UserRepository;
import com.aiary.be.auth.presentation.dto.SignupRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입: 신규 유저 등록
    public void save(SignupRequest request) {
        User newUser = User.builder()
            .email(request.email())
            .password(passwordEncoder.encode(request.password()))
            .userName(request.userName())
            .age(request.age())
            .role(request.role())
            .gender(request.gender())
            .phoneNumber(request.phoneNumber())
            .build();

        userRepository.save(newUser);
    }

    public User login(String email, String password){
        User user = userRepository.findUserByEmail(email)
            .orElse(null);

        if(user==null || !passwordEncoder.matches(password, user.getPassword()))
            return null;

        return user;
    }
}
