package com.yaloostore.front.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.yaloostore.front.auth.jwt.interceptor.JwtInterceptor;
import com.yaloostore.front.common.handler.CustomResponseErrorHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class RestTemplateConfig {


    private final RedisTemplate<String ,Object> redisTemplate;


    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .interceptors(new JwtInterceptor(redisTemplate))
                .customizers(restTemplate -> restTemplate.setRequestFactory(clientHttpRequestFactory()))
                .errorHandler(new CustomResponseErrorHandler(new ObjectMapper()))
                .build();
    }

    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();

        factory.setConnectTimeout(30000);
        factory.setReadTimeout(100000);
        factory.setBufferRequestBody(false);

        return factory;
    }



}
