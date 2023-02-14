package com.yaloostore.front.member.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.PrimitiveIterator;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SignUpRequest {

    @Size(min = 2, max = 50)
    @NotBlank(message = "공백과 빈값은 허용하지 않습니다. 올바르게 입력해주세요.")
    @JsonProperty("name")
    private String name;

    @NotBlank(message = "공백과 빈값은 허용하지 않습니다. 올바르게 입력해주세요.")
    @Size(min = 2, max = 20)
    @Pattern(regexp = "^[a-zA-Z]+[0-9]*$", message ="아이디는 영문과 숫자로만 가능하며 숫자로 시작할 수 없습니다. 올바르게 입력해주세요.")
    @JsonProperty("id")
    private String id;

    @NotBlank(message = "공백과 빈값은 허용하지 않습니다. 올바르게 입력해주세요.")
    @JsonProperty("password")
    private String password;

    @NotBlank(message = "공백과 빈값은 허용하지 않습니다. 올바르게 입력해주세요.")
    @Size(min = 2, max = 30)
    @Pattern(regexp = "^[가-힣a-zA-Z]{2,30}$", message = "닉네임은 한글과 영문만 입력해주세요.")
    @JsonProperty("nickname")
    private String nickname;

    @JsonProperty("gender")
    @NotBlank(message = "공백과 빈값은 허용하지 않습니다. 올바르게 입력해주세요.")
    private String gender;

    @JsonProperty("emailAddress")
    @Email
    @NotBlank(message = "공백과 빈값은 허용하지 않습니다. 올바르게 입력해주세요.")
    @Size(min = 2, max = 100)
    private String emailAddress;


    @JsonProperty("birthday")
    @NotBlank(message = "공백과 빈값은 허용하지 않습니다. 올바르게 입력해주세요.")
    @Pattern(regexp = "^[0-9]{8}", message = "ex) 1999090 숫자로만 8글자 작성해주세요")
    private String birthday;


    @JsonProperty("phoneNumber")
    @NotBlank(message = "공백과 빈값은 허용하지 않습니다. 올바르게 입력해주세요.")
    @Size(min = 11, max = 30)
    private String phoneNumber;

    public void setPassword(String password) {
        this.password = password;
    }
}
