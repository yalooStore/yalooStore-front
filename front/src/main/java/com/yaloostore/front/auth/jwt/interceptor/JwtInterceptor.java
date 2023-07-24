package com.yaloostore.front.auth.jwt.interceptor;

import com.yaloostore.front.auth.jwt.meta.AuthInformation;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.yaloostore.front.auth.utils.AuthUtil.HEADER_UUID;
import static com.yaloostore.front.auth.utils.AuthUtil.JWT;
/**
 * RestTemplate을 실행할 때 해당 인증 정보가 있다면 해당 정보를 넘겨주기 위한 인터셉트 설정을 위한 클래스입니다.
 * */
@RequiredArgsConstructor
@Slf4j
public class JwtInterceptor implements ClientHttpRequestInterceptor {

    private final RedisTemplate<String, Object> redisTemplate;


    private final List<String> EXCLUDED_PATHS = Arrays.asList("/api/service/products/new/stock/book",
            "/error", "/api/service/products/new/stock/book", "/api/service/product-types");

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution
    ) throws IOException {
        String path = request.getURI().getPath();
        log.info("path={}", path);

        if (EXCLUDED_PATHS.contains(path)){
            return execution.execute(request, body);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {

            HttpServletRequest servletRequest = Objects.requireNonNull(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()))
                    .getRequest();

            String uuid = getUuidFromCookie(servletRequest.getCookies());
            log.info("uuid={}", uuid);

            if (Objects.isNull(uuid)) {
                return execution.execute(request, body);
            }

            AuthInformation auth = (AuthInformation) redisTemplate.opsForHash().get(uuid, JWT.getValue());
            if (Objects.nonNull(auth)) {
                log.info("accessToken={}", auth.getAccessToken());
                request.getHeaders().setBearerAuth(auth.getAccessToken());
                request.getHeaders().add(HEADER_UUID.getValue(), uuid);
            }
        }
        return execution.execute(request, body);
    }



    private String getUuidFromCookie(Cookie[] cookies) {

        if (Objects.isNull(cookies)) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (Objects.equals(HEADER_UUID.getValue(), cookie.getName())) {
                return cookie.getValue();
            }
        }

        return null;

    }
}
