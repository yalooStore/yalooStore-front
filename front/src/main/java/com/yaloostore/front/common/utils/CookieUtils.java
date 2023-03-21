package com.yaloostore.front.common.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
public class CookieUtils {


    /**
     * Cookie 생성 메소드
     *
     * @param name 쿠키 이름
     * @param value 쿠키 값
     * @param maxAge 쿠키가 살아있는 최대 시간
     *
     * @return Cookie 생성된 쿠키
     * */
    public Cookie setupCookie(String name, String value, int maxAge){

        Cookie cookie = new Cookie(name,value);

        //모든 경로에서 쿠키 사용
        cookie.setPath("/");

        //서버로 http request 요청을 보낼때만 쿠키를 전송 & XSS 공격 차단
        cookie.setHttpOnly(true);

        cookie.setMaxAge(maxAge);

        return cookie;
    }

    /**
     * 최대 시간 지정하지 않은 쿠키 생성 메소드
     * */
    public Cookie setupCookie(String name, String value){

        Cookie cookie = new Cookie(name,value);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }


    /**
     * cookie value를 불러오는 메소드
     *
     * @param cookies 쿠키 목록
     * @param key 조회하려하는 쿠키 값
     * @return 조회대상 쿠키값
     * */
    public String getValue(Cookie[] cookies, String key){

        if(Objects.isNull(cookies)){
            return null;
        }
        for (Cookie cookie : cookies) {
            if (Objects.equals(key, cookie.getName())){
                return cookie.getValue();
            }
        }

        return null;
    }


    /**
     * 쿠키에 저장된 장바구니 관련 정보와 redis 정보를 삭제 메소드
     *
     * @param redisTemplate 저장된 장바구니 관련 정보와 함께 레디스 정보도 삭제할 때 사용할 레디스 템플릿
     * @param cookie 쿠키
     * @param response 쿠키 삭제 후 응답에 cookie 값 세팅을 위한 응답 객체
     * */
    public void delete(RedisTemplate<String, Objects> redisTemplate,
                       Cookie cookie,
                       HttpServletResponse response){
        if(Objects.nonNull(cookie)){
            String value = cookie.getValue();
            redisTemplate.delete(value);

            //쿠키값 초기화
            Cookie cart = this.setupCookie("CART_NO", "", 0);
            response.addCookie(cart);
        }


    }






}
