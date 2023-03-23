package com.yaloostore.front.member.adapter;


import com.yaloostore.front.config.GatewayConfig;
import com.yaloostore.front.member.dto.request.LogoutRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;


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


    public void logout(String uuid, String accessToken){
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        LogoutRequest logoutRequest = new LogoutRequest(uuid);

        HttpEntity<LogoutRequest> entity = new HttpEntity<>(logoutRequest, headers);

        //getAuth server로 보내야하지만 서버를 지금은 두개만 쓸거라 shop으로 보낸다.
        URI uri = UriComponentsBuilder.fromUriString(gatewayConfig.getShopUrl())
                .path("/logout")
                .encode()
                .build()
                .toUri();

        restTemplate.exchange(
                uri,
                HttpMethod.POST,
                entity,
                Void.class
        );
    }

}
