package com.yaloostore.front.auth;

import com.yaloostore.front.member.exception.InvalidLoginRequestException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import java.io.IOException;
import java.util.Objects;

public class CustomAuthenticationManager extends AbstractAuthenticationProcessingFilter {


    public CustomAuthenticationManager(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String loginId = request.getParameter("loginId");
        String password = request.getParameter("password");

        if(Objects.isNull(loginId) && Objects.isNull(password)){
            throw new InvalidLoginRequestException();
        }

        return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(loginId, password));


        return null;
    }
}
