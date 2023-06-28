package com.yaloostore.front.auth.interceptor;


import com.yaloostore.front.auth.adapter.AuthAdapter;
import com.yaloostore.front.auth.utils.CookieUtils;
import com.yaloostore.front.auth.jwt.AuthInformation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.yaloostore.front.auth.utils.AuthUtil.*;


@RequiredArgsConstructor
public class JwtTokenReIssueInterceptor implements HandlerInterceptor {

    private final CookieUtils cookieUtils;
    private final RedisTemplate<String, Object> redisTemplate;
    private final AuthAdapter authAdapter;

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
            ResponseEntity<Void> response = authAdapter.tokenReissue(uuid);

            String accessToken = response.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String expiredTime = response.getHeaders().get(HEADER_EXPIRED_TIME.getValue()).get(0);

            auth.setAccessToken(accessToken);
            auth.setExpiredTime(expiredTime);

            redisTemplate.opsForHash().put(uuid, JWT.getValue(), auth);

        }
    }

    private boolean isReissueRequired(AuthInformation auth) {
        long expiredTime = Long.parseLong(auth.getExpiredTime());
        long now = new Date().getTime();

        return expiredTime - (now/1000) > Duration.ofMinutes(59).toSeconds();

    }
}
