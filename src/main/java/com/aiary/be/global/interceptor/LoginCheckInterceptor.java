package com.aiary.be.global.interceptor;


import com.aiary.be.auth.presentation.dto.UserResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
        HttpServletRequest request,
        HttpServletResponse response,
        Object handler) throws Exception {

        log.info("interceptor preHandle request url: {}", request.getRequestURI());

        HttpSession session = request.getSession();

        if(session==null || session.getAttribute("loggedInUser")==null){
            log.warn("인증되지 않은 사용자, url: {}", request.getRequestURI());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"message\": \"로그인이 필요합니다.\"}");

            return false;
        }

        // 이후 다른 페이지 인가 구현 예정
//        if(request.getRequestURI().startsWith("/api/admin") &&
//        !session.getAttribute("userRole").equals("ADMIN")){
//            log.warn("일반 사용자는 이용할 수 없습니다.");
//            ...
//        }

        // controller에서 자주 사용할 만한 것들은 @RequestAttribute()로 받아서 사용할 수 있도록 전달할 수 있다.
//        request.setAttribute("userEmail", session.getAttribute("userEmail"));
        UserResponse userResponse = (UserResponse) session.getAttribute("loggedInUser");
        request.setAttribute("userId", userResponse.userId());
        return true;
    }

    @Override
    public void postHandle(
        HttpServletRequest request,
        HttpServletResponse response,
        Object handler,
        ModelAndView modelAndView) throws Exception {

        log.info("interceptor postHandle request url: {}", request.getRequestURI());

        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(
        HttpServletRequest request,
        HttpServletResponse response,
        Object handler, Exception ex) throws Exception {

        log.info("interceptor afterCompletion request url: {}", request.getRequestURI());

        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
