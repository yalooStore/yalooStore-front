package com.yaloostore.front.member.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.PrimitiveIterator;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SignUpRequest {

    @NotBlank(message = "공백과 빈값은 허용하지 않습니다. 올바르게 입력해주세요.")
    @Size(min = 2, max = 20)
    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}$", message ="아이디는 영문과 숫자로만 가능하며 숫자로 시작할 수 없습니다. 올바르게 입력해주세요.")
    private String id;

    @NotBlank(message = "공백과 빈값은 허용하지 않습니다. 올바르게 입력해주세요.")
    @Size(min = 2, max = 30)
    @Pattern(regexp = "^[가-힣ㄱ-ㅎa-zA-Z0-9._-]{2,15}$", message = "닉네임은 한글과 영문만 입력해주세요.")
    private String nickname;

    @Size(min = 2, max = 50)
    @NotBlank(message = "공백과 빈값은 허용하지 않습니다. 올바르게 입력해주세요.")
    @Pattern(regexp = "^[가-힣a-zA-Z]{2,50}$")
    private String name;

    @NotBlank(message = "공백과 빈값은 허용하지 않습니다. 올바르게 입력해주세요.")
    private String gender;

    @NotBlank(message = "공백과 빈값은 허용하지 않습니다. 올바르게 입력해주세요.")
    @Pattern(regexp = "^[0-9]{8}", message = "ex) 1999090 숫자로만 8글자 작성해주세요")
    private String birthday;

    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$",
            message = "비밀번호는 최소 8자 최대 20자, 하나 이상의 문자와 하나의 숫자 및 하나의 특수 문자가 들어가야 합니다.")
    private String password;

    @NotBlank(message = "공백과 빈값은 허용하지 않습니다. 올바르게 입력해주세요.")
    @Size(min = 11, max = 30)
    private String phoneNumber;

    @Email
    @NotBlank(message = "공백과 빈값은 허용하지 않습니다. 올바르게 입력해주세요.")
    @Size(min = 2, max = 100)
    private String emailAddress;

}
