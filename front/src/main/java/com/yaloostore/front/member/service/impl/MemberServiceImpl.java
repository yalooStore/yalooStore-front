package com.yaloostore.front.member.service.impl;

import com.yalooStore.common_utils.dto.ResponseDto;
import com.yaloostore.front.config.GatewayConfig;
import com.yaloostore.front.member.dto.request.SignUpRequest;
import com.yaloostore.front.member.dto.response.SignUpResponse;
import com.yaloostore.front.member.service.inter.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.core.Transient;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;


@Service
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final PasswordEncoder passwordEncoder;
    private final RestTemplate restTemplate;
    private final GatewayConfig gatewayConfig;


    /**
     * {@inheritDoc}
     * */
    @Override
    public SignUpResponse signUp(SignUpRequest request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));

        HttpEntity<SignUpRequest> entity = getHttpEntity(request);



        ResponseEntity<ResponseDto<SignUpResponse>> response = restTemplate.exchange(
                gatewayConfig.getShopUrl() + "/api/service/members/sign-up",
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<>() {
                }
        );

        log.info("response = {}", response.getBody().getData());
        return response.getBody().getData();
    }

    private static HttpEntity<SignUpRequest> getHttpEntity(SignUpRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<SignUpRequest> entity = new HttpEntity<>(request, headers);
        return entity;
    }
}
