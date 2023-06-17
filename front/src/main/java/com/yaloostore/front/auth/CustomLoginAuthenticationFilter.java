package com.yaloostore.front.auth;

import com.yaloostore.front.auth.exception.InvalidHttpMethodRequestException;
import com.yaloostore.front.member.exception.InvalidLoginRequestException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.websocket.OnClose;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.util.Objects;


public class CustomLoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final AuthenticationManager authenticationManager;

    private static final String LOGIN_ID_PARAMETER = "loginId";
    private static final String PASSWORD_PARAMETER = "password";

    private static final AntPathRequestMatcher DEFAULT_FORM_LOGIN_REQUEST_MATCHER = new AntPathRequestMatcher("/members/login", "POST");


    public CustomLoginAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(DEFAULT_FORM_LOGIN_REQUEST_MATCHER);
        this.authenticationManager = authenticationManager;
    }


    /**
     * form 로그인을 시도할 때 작동하는 필터기능으로 입력받은 로그인아이디와 패스워드를 기반으로 인증을 요청합니다.
     * */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        if(!request.getMethod().equals("POST")){
            throw new InvalidHttpMethodRequestException(request.getMethod().toString());
        }

        String loginId = request.getParameter(LOGIN_ID_PARAMETER);
        String password = request.getParameter(PASSWORD_PARAMETER);

        if(Objects.isNull(loginId) || Objects.isNull(password) || loginId.isBlank() || password.isBlank()){
            throw new InvalidLoginRequestException();
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginId, password);

        return authenticationManager.authenticate(authenticationToken);
    }
}
