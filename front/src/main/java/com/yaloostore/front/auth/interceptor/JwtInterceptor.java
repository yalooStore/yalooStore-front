package com.yaloostore.front.auth.interceptor;


import com.yaloostore.front.auth.jwt.AuthInformation;
import com.yaloostore.front.auth.utils.CookieUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
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
import java.util.Objects;

import static com.yaloostore.front.auth.utils.AuthUtil.HEADER_UUID;
import static com.yaloostore.front.auth.utils.AuthUtil.JWT;
/**
 * RestTemplate을 실행할 때 해당 인증 정보가 있다면 해당 정보를 넘겨주기 위한 인터셉트 설정을 위한 클래스입니다.
 * */
@RequiredArgsConstructor
public class JwtInterceptor implements ClientHttpRequestInterceptor {

    private final CookieUtils cookieUtils;
    private final RedisTemplate<String, Object> redisTemplate;


    /**
     * @param request HttpRequest 해당 요청을 ClientHttpRequestInterceptor이 낚아챕니다.
     * @param body 요청 바디입니다.
     * @param execution 실제 수행을 실행하고 요청을 다음 체인으로 전달할 때 사용하는 객체입니다.
     * @return 클라이언트의 http 응답
     * */
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        //인증을 완료한 경우라면 익명객체가 아닌 다른 usernamePasswordAuthenticationToken이 있을것
        if (!(authentication instanceof AnonymousAuthenticationToken)){
            HttpServletRequest servletRequest = Objects.requireNonNull(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()))
                    .getRequest();

            Cookie[] cookies = servletRequest.getCookies();
            String uuid = cookieUtils.getUuidFromCookie(cookies, HEADER_UUID.getValue());

            if (Objects.isNull(uuid)){
                return execution.execute(request, body);
            }
            AuthInformation authInformation = (AuthInformation) redisTemplate.opsForHash().get(uuid, JWT.getValue());


            /**
             * redis에 저장해둔 회원 객체를 사용해서 헤당 정보들을 추가해줍니다.
             * header측에 아래 두개의 정보 추가
             * */
            if(Objects.nonNull(authInformation)){
                request.getHeaders().setBearerAuth(authInformation.getAccessToken());
                request.getHeaders().add(HEADER_UUID.getValue(), uuid);
            }
        }
        return execution.execute(request, body);
    }
}
