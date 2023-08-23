package com.yaloostore.front.auth.jwt.interceptor;


import com.yaloostore.front.auth.adapter.AuthAdapter;
import com.yaloostore.front.common.utils.CookieUtils;
import com.yaloostore.front.auth.jwt.meta.AuthInformation;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.Duration;
import java.util.Date;
import java.util.Objects;

import static com.yaloostore.front.auth.utils.AuthUtil.*;


@RequiredArgsConstructor
@Slf4j
public class JwtTokenReIssueInterceptor implements HandlerInterceptor {

    private final CookieUtils cookieUtils;
    private final RedisTemplate<String, Object> redisTemplate;
    private final AuthAdapter authAdapter;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        log.info("jwt reissue interceptor request url = {}", requestURI);


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uuid = cookieUtils.getUuidFromCookie(request.getCookies(), HEADER_UUID.getValue());

        log.info("uuid = {}", uuid);

        log.info(String.valueOf(authentication.getClass()));
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            if (Objects.isNull(uuid)){
                log.info("토큰 재발급이 필요 없음");
                return true;
            }

            tokenReissue(uuid);
            log.info("토큰 재발급 끝");
        }
        return true;
    }

    private void tokenReissue(String uuid) {
        AuthInformation auth = (AuthInformation) redisTemplate.opsForHash().get(uuid, JWT.getValue());
        log.info("auth expired time = {}", auth.getExpiredTime());


        if(Objects.nonNull(auth) && isReissueRequired(auth)){
            ResponseEntity<Void> response = authAdapter.tokenReissue(uuid);
            log.info("message {}", response.getStatusCode());

            String accessToken = response.getHeaders().get("Authorization").get(0);
            String expiredTime = response.getHeaders().get(HEADER_EXPIRED_TIME.getValue()).get(0);

            HttpHeaders headers = response.getHeaders();

            headers.getFirst(HttpHeaders.AUTHORIZATION);
            response.getHeaders().getFirst(HEADER_UUID.getValue());
            response.getHeaders().getExpires();

            log.info(expiredTime);
            auth.setAccessToken(accessToken);
            auth.setExpiredTime(expiredTime);

            redisTemplate.opsForHash().put(uuid, JWT.getValue(), auth);

        }
    }

    private boolean isReissueRequired(AuthInformation authInfo) {
        long expiredTime = Long.parseLong(authInfo.getExpiredTime());
        long now = new Date().getTime();
        return (expiredTime - (now / 1000) < Duration.ofMinutes(59).toSeconds());
    }
}
