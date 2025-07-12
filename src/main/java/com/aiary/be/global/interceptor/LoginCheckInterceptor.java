package com.aiary.be.global.interceptor;


import com.aiary.be.global.exception.CustomException;
import com.aiary.be.global.exception.errorCode.UserErrorCode;
import com.aiary.be.user.presentation.dto.UserResponse;
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

            throw CustomException.from(UserErrorCode.REQUIRED_LOGIN);

        }
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
