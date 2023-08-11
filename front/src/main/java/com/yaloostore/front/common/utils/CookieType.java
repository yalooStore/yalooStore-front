package com.yaloostore.front.common.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum CookieType {

    CART_NO("CART_NO"),
    ;


    private String cookieType;

}
