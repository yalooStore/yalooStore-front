package com.yaloostore.front.auth;

import com.yaloostore.front.auth.adapter.AuthAdapter;
import com.yaloostore.front.auth.utils.CookieUtils;
import com.yaloostore.front.auth.jwt.meta.AuthInformation;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import java.util.Objects;

import static com.yaloostore.front.auth.utils.AuthUtil.HEADER_UUID;
import static com.yaloostore.front.auth.utils.AuthUtil.JWT;

@RequiredArgsConstructor
@Slf4j
public class CustomLogoutHandler implements LogoutHandler {

    private final RedisTemplate<String, Object> redisTemplate;
    private final CookieUtils cookieUtils;
    private final AuthAdapter authAdapter;


    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        //1. 세션이 존재할 경우에만 세션을 반환하고 존재하지 않을 땐 새로 생성 없이 null 반환
        HttpSession session = request.getSession(false);

        //1-2. 세션이 없을 땐 해당 로직을 그냥 종료
        if(Objects.isNull(session)){
            log.info("already logout");
            return;
        }
        //3. 세션 완전 삭제 작업
        session.invalidate();

        String uuid = cookieUtils.getUuidFromCookie(request.getCookies(), HEADER_UUID.getValue());

        if(Objects.isNull(uuid)){
            return;
        }

        AuthInformation authInformation = (AuthInformation) redisTemplate.opsForHash().get(uuid, JWT.getValue());

        //레디스에서 해당
        redisTemplate.opsForHash().delete(uuid, JWT.getValue());


        Cookie authCookie = cookieUtils.createCookie(HEADER_UUID.getValue(), "", 0);
        response.addCookie(authCookie);

        authAdapter.logout(uuid, authInformation.getAccessToken());

        SecurityContext context = SecurityContextHolder.getContext();
        SecurityContextHolder.clearContext();
        context.setAuthentication(null);


    }


}
