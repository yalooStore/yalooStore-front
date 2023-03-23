package com.yaloostore.front.member.dto.request;


import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LoginRequest {

    private String loginId;
    private String password;
}
