package com.yaloostore.front.member.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberLoginRequest {

    private String loginId;
    private String password;

}
