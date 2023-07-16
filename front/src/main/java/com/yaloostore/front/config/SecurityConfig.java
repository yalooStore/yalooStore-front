package com.yaloostore.front.config;


import com.yaloostore.front.auth.*;
import com.yaloostore.front.auth.adapter.AuthAdapter;
import com.yaloostore.front.auth.utils.CookieUtils;
import com.yaloostore.front.member.adapter.MemberAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {


    private final RedisTemplate<String ,Object> redisTemplate;
    private final MemberAdapter memberAdapter;
    private final CookieUtils cookieUtils;
    private final AuthAdapter authAdapter;


    /**
     * /members/** 작업은 ROLE_USER만 가능한 작업들
     * / welcome page는 모두 접근 가능 ( 그 이외는 모두 로그인 필요)
     * */

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/mypage/**").hasRole("ROLE_USER")
                .requestMatchers("/manage/**").hasRole("ROLE_ADMIN")
                .requestMatchers("/admin/**").hasRole("ROLE_ADMIN")
                .anyRequest().permitAll());
        http.formLogin()
                .loginPage("/members/login")
                .disable();

        http.logout()
                .logoutUrl("/logout")
                .addLogoutHandler(customLogoutHandler());
        //세션 대신 jwt 사용으로 해당 작업에서 사용하지 않음을 명세
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterAt(customLoginAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        http.headers().defaultsDisabled().frameOptions().sameOrigin();
        http.csrf().disable();
        http.cors().disable();

        return http.build();
    }

    /**
     * UsernamePasswordAuthenticationFilter를 대체하여 사용하는 필터를 빈으로 등록해줍니다.
     * */
    @Bean
    public CustomLoginAuthenticationFilter customLoginAuthenticationFilter(){

        CustomLoginAuthenticationFilter customLoginAuthenticationFiler = new CustomLoginAuthenticationFilter("/auth-login");
        customLoginAuthenticationFiler.setAuthenticationFailureHandler(authenticationFailureHandler());
        customLoginAuthenticationFiler.setAuthenticationManager(customAuthenticationManager());
        return customLoginAuthenticationFiler;
    }


    @Bean
    public CustomAuthenticationManager customAuthenticationManager(){
        return new CustomAuthenticationManager(memberAdapter, authAdapter,redisTemplate, cookieUtils);
    }


    /**
     * 로그아웃 작업 시 작동하는 customLogoutHandler입니다.
     * */
    @Bean
    public CustomLogoutHandler customLogoutHandler() {
        return new CustomLogoutHandler(redisTemplate, cookieUtils, authAdapter);
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler(){
        return new CustomAuthenticationFailureHandler();
    }

    /**
     * PasswordEncoder 빈 등록 메소드
     * @return 회원가입 시 비밀번호를 평문으로 저장하지 않기 위해서 등록한 Bean
     * */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


//    @Bean
//    public FilterRegistrationBean<JwtTokenAuthenticationFilter> jwtTokenAuthenticationFilter(){
//        FilterRegistrationBean<JwtTokenAuthenticationFilter> registrationFilter = new FilterRegistrationBean<>();
//
//        registrationFilter.setFilter(new JwtTokenAuthenticationFilter(cookieUtils, redisTemplate));
//        registrationFilter.addUrlPatterns("/**/login","/auth-login");
//
//        return registrationFilter;
//    }
//

}
