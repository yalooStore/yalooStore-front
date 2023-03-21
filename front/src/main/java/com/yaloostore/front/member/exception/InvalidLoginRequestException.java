package com.yaloostore.front.member.exception;


/**
 * 로그인 요청시 잘못된 입력값이 들어오면 발생하는 예외 클래스입니다.
 * */
public class InvalidLoginRequestException extends RuntimeException {

    public InvalidLoginRequestException() {
        super("INVALID LOGIN ID, PASSWORD");
    }
}
