package com.yaloostore.front.auth;

import com.yaloostore.front.common.utils.CookieUtils;
import com.yaloostore.front.member.adapter.MemberAdapter;
import com.yaloostore.front.member.dto.request.LoginRequest;
import com.yaloostore.front.member.dto.request.MemberLoginRequest;
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
    private static final String AUTHENTICATION = "Authentication";
    private static final String PREFIX_BEARER = "Bearer ";

    private static final String UUID_HEADER = "UUID_HEADER";
    private static final  String X_EXPIRE_HEADER = "X-Expire";
    private final MemberAdapter memberAdapter;
    private final RedisTemplate<String, Object> redisTemplate;

    private final CookieUtils cookieUtils;



    /**
     * 들어온 요청을 인증,인가 서버에서 위임해서 해당 jwt를 넘겨받아 해당 토큰이 유효한지 확인하고 유효하다면 해당 정보를 이용해서 작업을 진행할 수 있게 한다.
     * 해당 작업에서는
     * */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        MemberLoginRequest memberLoginRequest = new MemberLoginRequest(
                //인증정보 - 사용자 id
                (String) authentication.getPrincipal(),
                //인증정보 - 비밀번호
                (String) authentication.getCredentials()
        );


        ResponseEntity<Void> memberAuth = memberAdapter.getMemberAuth(memberLoginRequest);



        return null;
    }
}
