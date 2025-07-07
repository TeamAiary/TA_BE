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
        User newUser = new User(
            request.email(),
            request.password(),
            request.userName(),
            request.age(),
            request.role(),
            request.gender(),
            request.phoneNumber(),
            passwordEncoder
        );

        userRepository.save(newUser);
    }

    public User login(String email, String password){
        User user = userRepository.findUserByEmail(email)
            .orElse(null);

        if(user==null || !user.passwordMatches(password, passwordEncoder))
            return null;

        return user;
    }
}
