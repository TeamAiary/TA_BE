package com.aiary.be.global.util;

import com.aiary.be.user.presentation.dto.UserResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class SessionUtil{

    // 세션에 사용자 정보 저장
    // UserResponse 객체 자체를 session에 저장
    public static void setUserSession(HttpServletRequest request, UserResponse user) {
        HttpSession session = request.getSession();
        session.setAttribute("loggedInUser", user);
        
        session.setMaxInactiveInterval(30 * 60);
    }

    // 세션을 무효화(로그아웃)
    public static void invalidateSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // 세션 무효화
        }
    }

}
