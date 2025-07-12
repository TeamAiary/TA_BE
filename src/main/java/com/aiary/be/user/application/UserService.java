package com.aiary.be.user.application;

import com.aiary.be.global.exception.CustomException;
import com.aiary.be.global.exception.errorCode.UserErrorCode;
import com.aiary.be.user.domain.User;
import com.aiary.be.user.persistent.UserRepository;
import com.aiary.be.user.presentation.dto.UserRequest;
import com.aiary.be.user.presentation.dto.UserResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // for UserApiController
    // HttpServletRequest에서 세션에 저장된 userId를 받아오기 때문에, 요청을 보낸 사용자에 대한 검증은 필요없음.
    public UserResponse getUserById(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> CustomException.from(UserErrorCode.NOT_FOUND));

        return UserResponse.from(user);
    }

    @Transactional
    public void updateUser(Long userId, UserRequest request) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> CustomException.from(UserErrorCode.NOT_FOUND));

        user.update(request.email(), request.password(), request.userName(), request.age(),
            request.gender(), request.phoneNumber(), passwordEncoder);

    }

    public void deleteUser(Long userId){
        userRepository.findById(userId)
            .orElseThrow(() -> CustomException.from(UserErrorCode.NOT_FOUND));

        userRepository.deleteById(userId);
    }
    
    public List<Boolean> getBooleanWeeklyMission(Long userId) {
        User user = userRepository.findById(userId)
                        .orElseThrow(() -> CustomException.from(UserErrorCode.NOT_FOUND));
        
        return user.getWeeklyMissionBool();
    }

    // for other domains

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
