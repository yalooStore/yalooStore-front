package com.yaloostore.front.config;


import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


/**
 * 게이트웨이 관련 설정 클래스
 * */

@Getter
@Configuration
public class GatewayConfig {

    @Value("{gateway.server.url}")
    private String gatewayServerUrl;


    @Value("${shop.server.url}")
    private String shopUrl;


    @Value("${auth.server.url}")
    private String authUrl;



}
