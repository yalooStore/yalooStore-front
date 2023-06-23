package com.yaloostore.front.auth;

import com.yaloostore.front.member.exception.InvalidLoginRequestException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import java.io.IOException;
import java.util.Objects;
@Slf4j
public class CustomLoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {


    private static final String LOGIN_ID_PARAMETER = "loginId";
    private static final String PASSWORD_PARAMETER = "password";

    public CustomLoginAuthenticationFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);

    }


    /**
     * form 로그인을 시도할 때 작동하는 필터기능으로 입력받은 로그인아이디와 패스워드를 기반으로 인증을 요청합니다.
     * */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {


        log.info("====================== front authenticationFilter start ======================");
        String loginId = request.getParameter(LOGIN_ID_PARAMETER);
        String password = request.getParameter(PASSWORD_PARAMETER);

        if(Objects.isNull(loginId) || Objects.isNull(password) || loginId.isBlank() || password.isBlank()){
            throw new InvalidLoginRequestException();
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginId, password);

        return getAuthenticationManager().authenticate(authenticationToken);
    }
}
