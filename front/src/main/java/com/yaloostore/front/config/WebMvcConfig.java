package com.yaloostore.front.config;

import com.yaloostore.front.auth.adapter.AuthAdapter;
import com.yaloostore.front.auth.jwt.interceptor.JwtTokenReIssueInterceptor;
import com.yaloostore.front.auth.utils.CookieUtils;
import com.yaloostore.front.common.interceptor.RequestLoggingInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {


    private final CookieUtils cookieUtils;
    private final RedisTemplate<String, Object> redisTemplate;
    private final AuthAdapter authAdapter;


    /**
     * cors를 적용할 url 패턴을 와일드카드(/**)를 사용해서 전역으로 적용하는 메소드입니다.
     * */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new JwtTokenReIssueInterceptor(cookieUtils, redisTemplate, authAdapter))
                .excludePathPatterns("/css/**", "/js/**", "/libs/**", "/**/static/**",
                        "/img/**", "/","/check/**", "/fonts/**", "/assets/**", "/error/**");


        registry.addInterceptor(new RequestLoggingInterceptor())
                .excludePathPatterns("/css/**", "/js/**", "/libs/**", "/**/static/**", "/img/**",
                        "/","/check/**", "/fonts/**", "/assets/**", "/error/**");

    }
}
