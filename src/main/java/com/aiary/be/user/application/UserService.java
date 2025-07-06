package com.aiary.be.user.application;

import com.aiary.be.global.exception.CustomException;
import com.aiary.be.global.exception.errorCode.UserErrorCode;
import com.aiary.be.user.domain.User;
import com.aiary.be.user.persistent.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    
    @Transactional(readOnly = true)
    public User getUserForDiary(Long userId) {
        return userRepository.findById(userId).orElseThrow(
            () -> CustomException.from(UserErrorCode.NOT_FOUND)
        );
    }
    
    @Transactional(readOnly = true)
    public List<User> getAllUser() {
        return userRepository.findAll();
    }
}
