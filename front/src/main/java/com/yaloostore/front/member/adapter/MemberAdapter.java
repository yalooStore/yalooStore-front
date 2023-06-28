package com.yaloostore.front.member.adapter;


import com.yalooStore.common_utils.dto.ResponseDto;
import com.yaloostore.front.config.GatewayConfig;
import com.yaloostore.front.member.dto.request.LogoutRequest;
import com.yaloostore.front.member.dto.request.MemberLoginRequest;
import com.yaloostore.front.member.dto.response.MemberResponseDto;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class MemberAdapter {

    private final GatewayConfig gatewayConfig;
    private final RestTemplate restTemplate;





    /**
     * auth 서버에서 넘어온 회원의 토큰이 유효한지를 확인한 뒤 해당 정보를 가지고 회원의 정보를 가져오는 메소드입니다.
     * */
    public ResponseEntity<ResponseDto<MemberResponseDto>> getMemberInfo(String loginId){

        HttpHeaders headers = new HttpHeaders();
        HttpEntity entity = new HttpEntity(headers);

        return restTemplate.exchange(gatewayConfig.getShopUrl() + "/api/service/members/login" + loginId,
                HttpMethod.GET,
                entity, new ParameterizedTypeReference<ResponseDto<MemberResponseDto>>() {}
        );

    }






}
