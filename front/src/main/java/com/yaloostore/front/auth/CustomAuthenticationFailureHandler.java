package com.yaloostore.front.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    /**
     * JWT 인증 로직으로 로그인을 진행할 때 회원 인증에 실패한 경우에 작동하는 핸들러입니다.
     *
     * 인증에 실패해서 로그인에 실패한 경우 다시 로그인화면으로 redirect해줍니다.
     * **/
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        response.sendRedirect("/members/login");
    }
}
