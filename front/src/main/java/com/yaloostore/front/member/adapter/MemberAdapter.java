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


    public void logout(String uuid, String accessToken){
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        LogoutRequest logoutRequest = new LogoutRequest(uuid);

        HttpEntity<LogoutRequest> entity = new HttpEntity<>(logoutRequest, headers);

        //Auth(인증, 인가 관련) 서버로 보내야하지만 서버를 지금은 두개만 쓸거라 shop 서버로 보낸다.
        URI uri = UriComponentsBuilder.fromUriString(gatewayConfig.getShopUrl())
                .path("/api/service/auth/logout")
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


    /**
     * 회원이 입력한 로그인 정보를 dto 객체로 만들어 해당 객체를 통해서 인증 서버로 인증 요청을 위임할 때 사용하는 메소드입니다.
     *
     * 해당 부분은 header로 넘어오는 uuid, jwt accessToken, token expired time 정보를 받으며 body는 해당 사항 없습니다.
     * */
    public ResponseEntity<Void> getMemberAuth(MemberLoginRequest memberLoginRequest){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<MemberLoginRequest> httpEntity = new HttpEntity(memberLoginRequest, headers);


        return restTemplate.exchange(
                gatewayConfig.getAuthUrl() + "/auth/login",
                HttpMethod.POST,
                httpEntity,
                Void.class
        );

    }


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
