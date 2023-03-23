package com.yaloostore.front.auth;

import com.yaloostore.front.common.utils.CookieUtils;
import com.yaloostore.front.member.adapter.MemberAdapter;
import com.yaloostore.front.member.dto.request.LoginRequest;
import com.yaloostore.front.member.exception.InvalidLoginRequestException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import java.io.IOException;
import java.lang.reflect.Member;
import java.util.Objects;


/**
 * AuthenticationManager custom한 클래스
 *
 * AuthenticationManager - SecurityContextHolder에서 Authentication을 담고 있다.
 * 실제 Authentication을 만들고 인증을 처리하는 인터페이스가 'AuthenticationManager'이다.
 * */
@RequiredArgsConstructor
public class CustomAuthenticationManager implements AuthenticationManager {


    private static final String UUID_HEADER = "UUID_HEADER";
    private static final  String X_EXPIRE_HEADER = "X-Expire";
    private final MemberAdapter memberAdapter;
    private final RedisTemplate<String, Objects> redisTemplate;

    private final CookieUtils cookieUtils;


    /**
     * JWT 토큰 기반으로 shop 서버에서 유저 정보를 요청한 뒤, UsernamePasswordAuthenticationToken을 만들어 돌려줌
     *
     * @param authentication 인증 객체
     * @return 인증 객체
     * @throws AuthenticationException 인증 실패 시 발생하는 예외
     * */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        LoginRequest loginRequest = new LoginRequest(

                //인증정보 - 사용자 id
                (String) authentication.getPrincipal(),
                //인증정보 - 비밀번호
                (String) authentication.getCredentials()
        );




        return null;
    }
}
