package com.yaloostore.front.auth;

import com.yaloostore.front.member.exception.InvalidLoginRequestException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.websocket.OnClose;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import java.io.IOException;
import java.util.Objects;

public class CustomLoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {


    public CustomLoginAuthenticationFilter(String s) {
        super(s);
    }


    /**
     * form 로그인을 시도할 때 작동하는 필터기능으로 입력받은 로그인아이디와 패스워드를 기반으로 인증을 요청합니다.
     * */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {


        String loginId = request.getParameter("loginId");
        String password = request.getParameter("password");

        if(Objects.isNull(loginId) || Objects.isNull(password)){
            throw new InvalidLoginRequestException();
        }
        return getAuthenticationManager()
                .authenticate(
                        new UsernamePasswordAuthenticationToken(loginId,password)
                );
    }
}
