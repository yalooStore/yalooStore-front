package com.yaloostore.front.member.service.impl;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.yalooStore.common_utils.dto.ResponseDto;
import com.yaloostore.front.config.GatewayConfig;
import com.yaloostore.front.config.ObjectMapperConfig;
import com.yaloostore.front.member.dto.response.MemberDuplicateDto;
import com.yaloostore.front.member.service.inter.QueryMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Objects;



@Slf4j
@RequiredArgsConstructor
@Service
public class QueryMemberServiceImpl implements QueryMemberService {

    private final RestTemplate restTemplate;
    private final GatewayConfig config;
    private final ObjectMapper objectMapper;

    private final static String PREFIX_CHECK_PATH ="/api/service/members/check";

    @Override
    public MemberDuplicateDto checkNickname(String nickname) {
        log.info("nickname : {}", nickname);

        HttpEntity entity = getEntity();


        URI uri = UriComponentsBuilder.fromUriString(config.getShopUrl())
                .path(PREFIX_CHECK_PATH+"Nickname/{nickname}")
                .encode()
                .build()
                .expand(nickname)
                .toUri();
        ResponseEntity<ResponseDto<MemberDuplicateDto>> response = restTemplate.exchange(uri, HttpMethod.GET, entity, new ParameterizedTypeReference<ResponseDto<MemberDuplicateDto>>() {
        });

        return response.getBody().getData();
    }

    @Override
    public MemberDuplicateDto checkPhoneNumber(String phone) {
        log.info("phone : {}", phone);

        HttpEntity entity = getEntity();


        URI uri = UriComponentsBuilder.fromUriString(config.getShopUrl())
                .path(PREFIX_CHECK_PATH+"Phone/{phone}")
                .encode()
                .build()
                .expand(phone)
                .toUri();
        ResponseEntity<ResponseDto<MemberDuplicateDto>> response = restTemplate.exchange(uri, HttpMethod.GET, entity, new ParameterizedTypeReference<ResponseDto<MemberDuplicateDto>>() {
        });

        return response.getBody().getData();
    }

    @Override
    public MemberDuplicateDto checkEmail(String email) {
        log.info("email : {}", email);

        HttpEntity entity = getEntity();


        URI uri = UriComponentsBuilder.fromUriString(config.getShopUrl())
                .path(PREFIX_CHECK_PATH+"Email/{email}")
                .encode()
                .build()
                .expand(email)
                .toUri();
        ResponseEntity<ResponseDto<MemberDuplicateDto>> response = restTemplate.exchange(uri, HttpMethod.GET, entity, new ParameterizedTypeReference<ResponseDto<MemberDuplicateDto>>() {
        });

        return response.getBody().getData();
    }

    @Override
    public MemberDuplicateDto checkLoginId(String loginId) {
        log.info("loginId : {}", loginId);

        HttpEntity entity = getEntity();

        URI uri = UriComponentsBuilder.fromUriString(config.getShopUrl())
                .path(PREFIX_CHECK_PATH+"LoginId/{loginId}")
                .encode()
                .build()
                .expand(loginId)
                .toUri();
        ResponseEntity<ResponseDto<MemberDuplicateDto>> response = restTemplate.exchange(uri, HttpMethod.GET, entity, new ParameterizedTypeReference<ResponseDto<MemberDuplicateDto>>() {
        });

        return response.getBody().getData();

    }

    private static HttpEntity getEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity entity = new HttpEntity<>(headers);
        return entity;
    }
}
