package com.yaloostore.front.member.adapter;


import com.yaloostore.front.config.GatewayConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;


/**
 * 회원 관련 로직 처리를 위한 shop, auth 서버에 restTemplate으로 요청을 위한 어댑터 클래스 입니다.
 *
 * ex) 회원 로그인 시에 사용.
 * */
@Component
@RequiredArgsConstructor
public class MemberAdapter {

    private final GatewayConfig gatewayConfig;
    private RestTemplate restTemplate;


}
