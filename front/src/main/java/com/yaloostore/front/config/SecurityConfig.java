package com.yaloostore.front.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    /**
     * /members/** 작업은 ROLE_USER만 가능한 작업들
     * / welcome page는 모두 접근 가능 ( 그 이외는 모두 로그인 필요)
     * */

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/mypage/**").hasRole("ROLE_USER")
                .requestMatchers("/manage/**").hasRole("ROLE_ADMIN")
                .requestMatchers("/", "/members/signup").permitAll());
                //.anyRequest().authenticated());



        http.formLogin(form -> form.loginPage("/members/login").permitAll()
                    .defaultSuccessUrl("/"));

        http.logout(logout -> logout
                .logoutUrl("/members/logout"));


        http.httpBasic().disable();

        http.headers().defaultsDisabled().frameOptions().sameOrigin();
        http.cors().disable();
        http.csrf().disable();
        return http.build();
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
