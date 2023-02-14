package com.yaloostore.front.member.dto.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SignUpResponse {

    private Long memberId;
    private String id;
    private String nickname;
    private String name;
    private String gender;
    private String birthday;
    private String password;
    private String phoneNumber;
    private String emailAddress;
}
