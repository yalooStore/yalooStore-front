package com.yaloostore.front.common.utils;


import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CookieUtils {


    public Cookie createCookie(String name,String value, int maxAge){
        Cookie cookie = new Cookie(name,value);

        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);

        return cookie;
    }
    public Cookie createCookieWithoutMaxAge(String name,String value){
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        return cookie;
    }


    /**
     * 입력받은 key로 해당하는 uuid를 찾아 돌려줍니다.
     * */
    public String getUuidFromCookie(Cookie[] cookies, String key){
        if (Objects.isNull(cookies)){
            return null;
        }
        for (Cookie cookie : cookies) {
            if(Objects.equals(cookie.getName(), key)){
                return cookie.getValue();
            }
        }
        return null;
    }

}
