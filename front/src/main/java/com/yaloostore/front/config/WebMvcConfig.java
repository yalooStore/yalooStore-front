package com.yaloostore.front.config;

import com.yaloostore.front.member.adapter.MemberAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {


    private final MemberAdapter memberAdapter;


    /**
     * cors를 적용할 url 패턴을 와일드카드(/**)를 사용해서 전역으로 적용하는 메소드입니다.
     * */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }
}
