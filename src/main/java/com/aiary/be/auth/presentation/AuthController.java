package com.aiary.be.auth.presentation;

import com.aiary.be.auth.application.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
        HttpServletResponse httpServletResponse // 쿠키 전달용
    ) {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    // Todo 써드 파티 OAuth
    @GetMapping("/oauth")
    public ResponseEntity<?> oauth(
        HttpServletResponse httpServletResponse // 쿠키 전달용
    ) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
