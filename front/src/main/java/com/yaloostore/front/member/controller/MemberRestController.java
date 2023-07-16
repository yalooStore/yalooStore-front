package com.yaloostore.front.member.controller;


import com.yaloostore.front.member.dto.response.MemberDuplicateDto;
import com.yaloostore.front.member.service.inter.QueryMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 회원정보 수정, 회원가입 시 중복되면 안 되는 필드 확인 작업을 진행합니다.
 * */
@RestController
@RequiredArgsConstructor
public class MemberRestController {

    private final QueryMemberService queryMemberService;

    @GetMapping("/check/checkNickname/{nickname}")
    public MemberDuplicateDto checkNicknameDuplicate(@PathVariable String nickname){
        return queryMemberService.checkNickname(nickname);
    }
    @GetMapping("/check/checkEmail/{email}")
    public MemberDuplicateDto checkEmailDuplicate(@PathVariable String email){
        return queryMemberService.checkEmail(email);
    }
    @GetMapping("/check/checkPhone/{phone}")
    public MemberDuplicateDto checkPhoneDuplicate(@PathVariable String phone){
        return queryMemberService.checkPhoneNumber(phone);
    }

    @GetMapping("/check/checkLoginId/{loginId}")
    public MemberDuplicateDto checkLoginIdDuplicate(@PathVariable(name = "loginId") String id){
        return queryMemberService.checkLoginId(id);
    }


}
