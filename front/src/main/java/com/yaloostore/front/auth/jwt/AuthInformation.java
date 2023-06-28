package com.yaloostore.front.auth.jwt;


import com.yaloostore.front.member.dto.response.MemberResponseDto;
import lombok.*;

import java.util.List;


/**
 * 회원정보 accessToken을 redis에 저장하여 관리할 떄 사용하는 클래스입니다.
 * */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AuthInformation {

    private String loginId;
    private String accessToken;
    private List<String> authorities;
    private String expiredTime;

    public AuthInformation(MemberResponseDto responseDto,
                            String accessToken,
                            String expiredTime){
        this.loginId = responseDto.getId();
        this.accessToken = accessToken;
        this.authorities = responseDto.getRoles();
        this.expiredTime = expiredTime;
    }

}
