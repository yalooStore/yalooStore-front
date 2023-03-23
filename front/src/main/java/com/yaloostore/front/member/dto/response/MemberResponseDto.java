package com.yaloostore.front.member.dto.response;

import lombok.*;

import java.util.List;


@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberResponseDto {

    private Long memberId;
    private String name;
    private String nickname;
    private String id;
    private String email;
    private String password;
    private List<String> roles;
}
