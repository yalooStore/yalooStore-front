package com.yaloostore.front.member.controller.web;


import com.yaloostore.front.member.dto.request.SignUpRequest;
import com.yaloostore.front.member.dto.response.SignUpResponse;
import com.yaloostore.front.member.exception.ValidationFailedException;
import com.yaloostore.front.member.service.inter.MemberService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.objenesis.ObjenesisException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Security;
import java.util.Objects;


@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberAuthWebController {


    private final MemberService memberService;


    /**
     * 회원가입 화면으로 이동합니다.
     * */
    @GetMapping("/signup")
    public String signupForm(@ModelAttribute(name = "member") SignUpRequest request){
        return "auth/signup-form";
    }


    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute(name ="member") SignUpRequest request,
                         BindingResult bindingResult,
                         Model model){

        log.info("request : {}",request.getName());

        if(bindingResult.hasErrors()){
            log.info("binding result : {}",bindingResult);
            log.info(bindingResult.getObjectName());
            return "redirect:/members/signup";
        }

        SignUpResponse response = memberService.signUp(request);

        model.addAttribute("response", response);

        return "auth/signup-success";

    }

    /**
     * 로그인 화면으로 가는 로그인 폼 컨트롤러입니다.
     * */
    @GetMapping("/login")
    public String loginForm(){


        //로그인 되어 로그인 유지 중인 회원이라면 로그인 로직에 접근하려 할 때 이를 막아줍니다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(Objects.nonNull(authentication)){
            return "/";
        }

        return "auth/login-form";
    }


}
