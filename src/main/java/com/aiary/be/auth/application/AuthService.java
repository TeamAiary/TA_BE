package com.aiary.be.auth.application;


import com.aiary.be.user.domain.Role;
import com.aiary.be.user.presentation.dto.UserResponse;
import com.aiary.be.global.exception.CustomException;
import com.aiary.be.global.exception.errorCode.UserErrorCode;
import com.aiary.be.user.domain.User;
import com.aiary.be.user.persistent.UserRepository;
import com.aiary.be.auth.presentation.dto.SignupRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입: 신규 유저 등록
    @Transactional
    public void save(SignupRequest request) {
        if(userRepository.existsByEmail(request.email())){
            throw CustomException.from(UserErrorCode.DUPLICATE_EMAIL);
        }

        User newUser = new User(
            request.email(),
            request.password(),
            request.userName(),
            request.age(),
            Role.PATIENT,
            request.gender(),
            request.phoneNumber(),
            passwordEncoder
        );

        userRepository.save(newUser);
    }
    
    @Transactional
    public UserResponse login(String email, String password){
        User user = userRepository.findUserByEmail(email)
            .orElseThrow(()-> CustomException.from(UserErrorCode.INVALID_EMAIL_PASSWORD));

        if(!user.passwordMatches(password, passwordEncoder)){
            throw CustomException.from(UserErrorCode.INVALID_EMAIL_PASSWORD);
        }

        return UserResponse.from(user);
    }
}
