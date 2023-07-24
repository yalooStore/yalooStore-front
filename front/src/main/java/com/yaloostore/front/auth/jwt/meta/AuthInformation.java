package com.yaloostore.front.auth.jwt.meta;


import com.yaloostore.front.member.dto.response.MemberLoginResponse;
import com.yaloostore.front.member.dto.response.MemberResponseDto;
import lombok.*;

import java.io.Serializable;
import java.util.List;


/**
 * 회원정보 accessToken을 redis에 저장하여 관리할 떄 사용하는 클래스입니다.
 * */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AuthInformation implements Serializable {

    private String loginId;
    private String accessToken;
    private List<String> authorities;
    private String expiredTime;

    public AuthInformation(MemberLoginResponse responseDto,
                           String accessToken,
                           String expiredTime){
        this.loginId = responseDto.getLoginId();
        this.accessToken = accessToken;
        this.authorities = responseDto.getRoles();
        this.expiredTime = expiredTime;
    }

}
