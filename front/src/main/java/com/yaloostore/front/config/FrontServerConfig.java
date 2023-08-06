package com.yaloostore.front.config;


import lombok.Getter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


/**
 *  프론트 서버 정보를 담는 설정 클래스입니다.
 * */
@Configuration
@Getter
public class FrontServerConfig {

    @Value("${yalooStore.front.url}")
    private String frontServerUrl;

}
