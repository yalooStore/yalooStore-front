package com.yaloostore.front.member.jwt;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthUtil {

    JWT_CODE("JWT"),
    UUID_CODE("AUTH_MEMBER");

    private final String value;


}
