package com.aiary.be.auth.presentation;

import com.aiary.be.auth.application.AuthService;
import com.aiary.be.auth.presentation.dto.LoginRequest;
import com.aiary.be.auth.presentation.dto.SignupRequest;
import com.aiary.be.global.response.Message;
import com.aiary.be.user.domain.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    // Todo 회원 가입 기능
    @PostMapping("/register")
    public ResponseEntity<?> register(
        @RequestBody SignupRequest signupRequest,
        HttpServletResponse httpServletResponse // 쿠키 전달용
    ) {
        authService.save(signupRequest);

        // 회원가입 시에도 client에게 줘야 하는 쿠키가 있나요?
        // 없으면 httpServletResponse는 이 메서드에서 사용 안 하는 걸로..

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // Todo 로그인 기능
    @PostMapping("/login")
    public ResponseEntity<?> login(
        @RequestBody LoginRequest loginRequest,
        BindingResult bindingResult,
        HttpServletRequest httpServletRequest,
        HttpServletResponse httpServletResponse // 쿠키 전달용
    ) {
        // 이후 bindingResult는 RequestBody의 유효성 검증 로직에 사용

        User user = authService.login(loginRequest.getEmail(), loginRequest.getPassword());
        if(user==null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Message.from("로그인 정보가 일치하지 않습니다."));
        }

        // WAS가 알아서 JSESSIONID 쿠키를 응답에 추가
        // userId, userName 필드를 따로 주지 않고, User 객체를 그대로 넣어줘도 될 듯
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute("userId", user.getId());
        session.setAttribute("userName", user.getUserName());

        return ResponseEntity.status(HttpStatus.OK)
            .body(Message.from("로그인에 성공하였습니다."));
    }

    // 로그아웃 기능
    @PostMapping("/logout")
    public ResponseEntity<?> logout(
        HttpServletRequest httpServletRequest
    ){
        // 기존 session 가져오기(없으면 null을 받아옴)
        HttpSession session = httpServletRequest.getSession(false);

        if(session!=null){
            // 기존 session 존재 -> 무효화 필요함
            session.invalidate();
        }

        return ResponseEntity.status(HttpStatus.OK)
            .body(Message.from("로그아웃에 성공하였습니다."));
    }

    // Todo 써드 파티 OAuth
    @GetMapping("/oauth")
    public ResponseEntity<?> oauth(
        HttpServletResponse httpServletResponse // 쿠키 전달용
    ) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
