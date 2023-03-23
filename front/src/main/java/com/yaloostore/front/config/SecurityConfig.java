package com.yaloostore.front.config;


import com.yaloostore.front.auth.CustomAuthenticationManager;
import com.yaloostore.front.auth.CustomLogoutHandler;
import com.yaloostore.front.common.utils.CookieUtils;
import com.yaloostore.front.member.adapter.MemberAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {


    private final RedisTemplate<String ,Object> redisTemplate;
    private final MemberAdapter memberAdapter;
    private final CookieUtils cookieUtils;


    /**
     * /members/** 작업은 ROLE_USER만 가능한 작업들
     * / welcome page는 모두 접근 가능 ( 그 이외는 모두 로그인 필요)
     * */

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/mypage/**").hasRole("ROLE_USER")
                .requestMatchers("/manage/**").hasRole("ROLE_ADMIN")
                .anyRequest().permitAll());

        http.formLogin()
                .loginPage("/members/login");

        http.logout()
                .logoutUrl("/logout")
                .addLogoutHandler(customLogoutHandler())
                .logoutSuccessUrl("/");

        http.httpBasic().disable();

        http.headers().defaultsDisabled().frameOptions().sameOrigin();
        http.cors().disable();
        http.csrf().disable();
        return http.build();
    }

    private CustomLogoutHandler customLogoutHandler() {
        return new CustomLogoutHandler(redisTemplate, memberAdapter,cookieUtils);
    }


    /**
     * PasswordEncoder 빈 등록 메소드
     *
     * @return 회원가입 시 비밀번호를 평문으로 저장하지 않기 위해서 등록한 Bean
     * */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }



}
