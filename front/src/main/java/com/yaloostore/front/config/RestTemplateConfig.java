package com.yaloostore.front.config;


import ch.qos.logback.core.net.server.Client;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yaloostore.front.auth.interceptor.JwtInterceptor;
import com.yaloostore.front.auth.utils.CookieUtils;
import com.yaloostore.front.config.handler.CustomResponseErrorHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;


@Configuration
@RequiredArgsConstructor
public class RestTemplateConfig {


    private final RedisTemplate<String ,Object> redisTemplate;
    private final CookieUtils cookieUtils;

    /**
     * @param restTemplateBuilder Restemplate의 크라이언트와 서버 커넥션 설정을 위한 빌더
     * @return RestTemplate 반환
     * */
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder){
        return restTemplateBuilder
                .interceptors(new JwtInterceptor(cookieUtils,redisTemplate))
                .setConnectTimeout(Duration.ofMillis(30000))
                .setReadTimeout(Duration.ofMillis(100000))
                .setBufferRequestBody(false)
                //.errorHandler(new CustomResponseErrorHandler(new ObjectMapper()))
                .build();

    }



}
