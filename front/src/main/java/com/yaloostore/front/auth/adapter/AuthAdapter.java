package com.yaloostore.front.auth.adapter;



import com.yaloostore.front.config.GatewayConfig;
import com.yaloostore.front.member.dto.request.LogoutRequest;
import com.yaloostore.front.member.dto.request.MemberLoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static com.yaloostore.front.auth.utils.AuthUtil.HEADER_UUID;

@Component
@RequiredArgsConstructor
public class AuthAdapter {


    private final GatewayConfig gatewayConfig;
    private final RestTemplate restTemplate;


    /**
     * 토큰 재발급에 사용됩니다.
     * 이를 위해서 해다 정보를 auth server로 넘겨서 해당 작업의 결과를 받아옵니다.
     * */
    public ResponseEntity<Void> tokenReissue(String uuid){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(HEADER_UUID.getValue(), uuid);

        URI uri = UriComponentsBuilder
                .fromUriString(gatewayConfig.getAuthUrl())
                .path("/auth/reissue")
                .encode()
                .build()
                .toUri();

        HttpEntity entity = new HttpEntity(headers);

        return restTemplate.exchange(uri,
                HttpMethod.POST,
                entity,
                Void.class);
    }

    public void logout(String uuid, String accessToken){
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        LogoutRequest logoutRequest = new LogoutRequest(uuid);

        HttpEntity<LogoutRequest> entity = new HttpEntity<>(logoutRequest, headers);

        //Auth(인증, 인가 관련) 서버로 보내야하지만 서버를 지금은 두개만 쓸거라 shop 서버로 보낸다.
        URI uri = UriComponentsBuilder.fromUriString(gatewayConfig.getAuthUrl())
                .path("/auth/logout")
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


}
