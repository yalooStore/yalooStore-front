package com.yaloostore.front.auth.interceptor;


import com.yaloostore.front.auth.utils.CookieUtils;
import com.yaloostore.front.member.jwt.AuthInformation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.websocket.OnClose;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.Duration;
import java.util.Date;
import java.util.Objects;

import static com.yaloostore.front.auth.utils.AuthUtil.HEADER_UUID;
import static com.yaloostore.front.auth.utils.AuthUtil.JWT;


@RequiredArgsConstructor
public class JwtTokenReIssueInterceptor implements HandlerInterceptor {

    private final CookieUtils cookieUtils;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uuid = cookieUtils.getUuidFromCookie(request.getCookies(), HEADER_UUID.getValue());

        if(!(authentication instanceof AnonymousAuthenticationToken)){
            if (Objects.isNull(uuid)){
                return true;
            }
            tokenReissue(uuid);
        }
        return true;
    }

    private void tokenReissue(String uuid) {
        AuthInformation auth = (AuthInformation) redisTemplate.opsForHash().get(uuid, JWT.getValue());

        if(Objects.nonNull(auth) && isReissueRequired(auth)){

        }
    }

    private boolean isReissueRequired(AuthInformation auth) {
        long expiredTime = Long.parseLong(auth.getExpiredTime());
        long now = new Date().getTime();

        return expiredTime - (now/1000) > Duration.ofMinutes(59).toSeconds();

    }
}
