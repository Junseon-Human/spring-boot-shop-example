package com.keduit.shop.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // ajax 비동기 통신의 경우 http request header에 XMLHttpRequest 값을 넣어줌.
        // 이때 인증되지 않은 사용자가 ajax로 리소스를 요청하면 "Unauthorized(401)"
        // 나머지는 로그인을 유도하기 위해 리다이렉트 함.
        if("XMLHttpRequest".equals(request.getHeader("x-requested-with"))) {
            response.sendError((HttpServletResponse.SC_UNAUTHORIZED), "UnAuthorized");
        } else {
            response.sendRedirect("/members/login");
        }
    }
}
