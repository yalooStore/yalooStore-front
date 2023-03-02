package com.yaloostore.front.member.controller;


import com.yaloostore.front.member.dto.request.SignUpRequest;
import com.yaloostore.front.member.dto.response.SignUpResponse;
import com.yaloostore.front.member.exception.ValidationFailedException;
import com.yaloostore.front.member.service.inter.MemberService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


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
    public String signupForm(){
        return "auth/signup-form";
    }


    @PostMapping("/signup")
    public String signup(@Valid SignUpRequest request,
                         BindingResult bindingResult,
                         Model model){

        log.info("dto = {}" , request);


        if(bindingResult.hasErrors()){
            throw new ValidationFailedException(bindingResult);
        }

        SignUpResponse signUpResponse = memberService.signUp(request);
        model.addAttribute("response", signUpResponse);

        return "auth/signup-success";

    }

    /**
     * 로그인 화면으로 가는 로그인 폼 컨트롤러입니다.
     * */
    @GetMapping("/login")
    public String loginForm(
            HttpSession session,
            @RequestParam(defaultValue = "/", name = "redirect-to") String redirectUrl){
        session.setAttribute("redirect-to", redirectUrl);
        return "auth/login-form";
    }



}
