package com.yaloostore.front.member.service.impl;

import com.yaloostore.front.config.GatewayConfig;
import com.yaloostore.front.member.dto.request.SignUpRequest;
import com.yaloostore.front.member.dto.response.SignUpResponse;
import com.yaloostore.front.member.service.inter.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.Transient;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@RequiredArgsConstructor

public class MemberServiceImpl implements MemberService {

    private final PasswordEncoder passwordEncoder;
    private final RestTemplate restTemplate;
    private final GatewayConfig gatewayConfig;


    @Override
    public SignUpResponse signUp(SignUpRequest request) {

        request.setPassword(passwordEncoder.encode(request.getPassword()));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<SignUpRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<SignUpResponse> response = restTemplate.exchange(
                gatewayConfig.getGatewayServerUrl() + "api/service/members/sign-up",
                HttpMethod.POST,
                entity,
                SignUpResponse.class
        );


        return response.getBody();
    }
}
