package com.yaloostore.front.member.adapter;


import com.yalooStore.common_utils.dto.ResponseDto;
import com.yaloostore.front.config.GatewayConfig;
import com.yaloostore.front.member.dto.request.LogoutRequest;
import com.yaloostore.front.member.dto.request.MemberLoginRequest;
import com.yaloostore.front.member.dto.response.MemberLoginResponse;
import com.yaloostore.front.member.dto.response.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
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
@Slf4j
@RequiredArgsConstructor
public class MemberAdapter {

    private final GatewayConfig gatewayConfig;
    private final RestTemplate restTemplate;



    /**
     * auth 서버에서 넘어온 회원의 토큰이 유효한지를 확인한 뒤 해당 정보를 가지고 회원의 정보를 가져오는 메소드입니다.
     * */
    public ResponseEntity<ResponseDto<MemberLoginResponse>> getMemberInfo(MemberLoginRequest loginRequest,
                                                                          String token){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity entity = new HttpEntity(headers);

        URI uri = UriComponentsBuilder
                .fromUriString(gatewayConfig.getShopUrl())
                .path("/api/service/members/login/{loginId}")
                .encode()
                .build()
                .expand(loginRequest.getLoginId())
                .toUri();

        log.info("front -> shop uri? = {}", uri);

        return restTemplate.exchange(
                uri,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {}
        );

    }






}
