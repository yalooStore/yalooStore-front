package com.yaloostore.front.auth;

import com.yalooStore.common_utils.dto.ResponseDto;
import com.yaloostore.front.auth.exception.InvalidHttpHeaderException;
import com.yaloostore.front.auth.utils.CookieUtils;
import com.yaloostore.front.member.adapter.MemberAdapter;
import com.yaloostore.front.member.dto.request.MemberLoginRequest;
import com.yaloostore.front.member.dto.response.MemberResponseDto;
import com.yaloostore.front.member.jwt.AuthInformation;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Objects;

import static com.yaloostore.front.auth.utils.AuthUtil.*;


/**
 * AuthenticationManager custom한 클래스
 *
 * AuthenticationManager - SecurityContextHolder에서 Authentication을 담고 있다.
 * 실제 Authentication을 만들고 인증을 처리하는 인터페이스가 'AuthenticationManager'이다.
 * */
@RequiredArgsConstructor
public class CustomAuthenticationManager implements AuthenticationManager {
    private final MemberAdapter memberAdapter;
    private final RedisTemplate<String, Object> redisTemplate;

    private final CookieUtils cookieUtils;




    /**
     * 들어온 요청을 인증,인가 서버에서 위임해서 해당 jwt를 넘겨받아 해당 토큰이 유효한지 확인하고 유효하다면 해당 정보를 이용해서 작업을 진행할 수 있게 한다.
     * 해당 작업에서는
     * */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        
        // auth 서버측으로 넘겨줄 dto 객체
        MemberLoginRequest memberLoginRequest = new MemberLoginRequest(
                //인증정보 - 사용자 id
                (String) authentication.getPrincipal(),
                //인증정보 - 비밀번호
                (String) authentication.getCredentials()
        );
        // auth 서버와 restTemplate 사용한 통신 (해당 통신에는 body는 넘어오지 않고 header부분에 custom하게 설정해줌)
        ResponseEntity<Void> request = memberAdapter.getMemberAuth(memberLoginRequest);

        //받아온 header 정보로 해당 토큰이 유효한지를 확인 해주면 된다.(유효하지 않다면 에러 발생)
        checkValidLoginRequest(request);


        String uuid = Objects.requireNonNull(request.getHeaders().get(HEADER_UUID).get(0));
        String expiredTime = Objects.requireNonNull(request.getHeaders().get(HEADER_EXPIRED_TIME).get(0));

        String accessToken = extractStringAccessToken(request);


        //인증에 성공한 회원 객체 정보를 이용해서 실제 회원정보를 저장해둔 api 서버에서 해당 회원의 정보를 가져옵니다.
        ResponseEntity<ResponseDto<MemberResponseDto>> memberInfo = memberAdapter.getMemberInfo(memberLoginRequest.getLoginId());
        MemberResponseDto memberInfoResponse = memberInfo.getBody().getData();

        // redis에 저장할 때 사용하는 직렬화 클래스
        AuthInformation authInformation = new AuthInformation(memberInfoResponse, accessToken, expiredTime);

        redisTemplate.opsForHash().put(uuid, JWT.getValue(), authInformation);

        //TODO: 쿠키를 사용해서 uuid를 저장하고 이를 restTemplate Interceptor에 적용해보자
        HttpServletResponse servletResponse = (HttpServletResponse) Objects.requireNonNull(RequestContextHolder.getRequestAttributes());

        Cookie authCookie = cookieUtils.createCookieWithoutMaxAge(HEADER_UUID.getValue(), uuid);
        servletResponse.addCookie(authCookie);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(authInformation.getLoginId(), "", authentication.getAuthorities());

        return authenticationToken;
    }

    private String extractStringAccessToken(ResponseEntity<Void> request) {

        String accessToken = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

        if(Objects.isNull(accessToken)){
            throw new InvalidHttpHeaderException("해당 인증 헤더가 비어있습니다.");
        }
        if(accessToken.startsWith("Bearer ")){
            accessToken = accessToken.substring(7);
        }

        return accessToken;
    }


    /**
     * 회원 로그인(인증) 요청에 해당 인증 서버에서 인증 작업을 진행하고 헤더로 돌려준 값을 확인하는 작업니다.
     *
     * 실패? 이때 헤더에 해당하는 인증 헤더가 없거나 해당하는 UUID가 없다면 인증에 실패한 것으로 간주해서 해당 에러를 던집니다.
     * 성공? 헤더에 인증 헤더와 UUID를 설정해주었기 때문에 이 두개의 작업이 잘 넘겨왔다면 해당 인증이 성공한 것으로 보아 다음 작업으로 넘어갑니다.
     * */
    private void checkValidLoginRequest(ResponseEntity<Void> request) {
        if(!request.getHeaders().containsKey(HEADER_UUID) ||
                request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
            throw new BadCredentialsException("자격 증명이 실패되었습니다. ");
        }
    }
}
