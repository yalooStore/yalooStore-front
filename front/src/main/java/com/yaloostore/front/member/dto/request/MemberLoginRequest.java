package com.yaloostore.front.member.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberLoginRequest {

    @NotBlank
    @Size(min = 8, max = 15)
    @Pattern(regexp = "^[a-zA-Z]+[0-9]*$", message = "영문(필수)과 숫자(옵션) 순서 로만 가능 합니다")
    private String loginId;

    @NotBlank
    @Size(min = 8)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
            message = "최소 8자, 하나 이상의 문자와 하나의 숫자 및 하나의 특수 문자")
    private String password;
}