package com.yaloostore.front.member.service.inter;

import com.yaloostore.front.member.dto.request.SignUpRequest;
import com.yaloostore.front.member.dto.response.SignUpResponse;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface MemberService {
    SignUpResponse signUp(SignUpRequest request);
}
