package com.yaloostore.front.member.service.inter;

import com.yaloostore.front.member.dto.request.SignUpRequest;
import com.yaloostore.front.member.dto.response.SignUpResponse;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface MemberService {

    /**
     * 회원가입과 관련한 서비스로직입니다.
     * 이 서비스 로직의 경우엔 비지니스 객체를 다른 서버에서 가져오는 형태로 진행됩니다.
     *
     * @param request 회원가입에 필요한 request dto 객체
     * @return 회원 가입 완료후의 response dto
     * */
    SignUpResponse signUp(SignUpRequest request);
}
