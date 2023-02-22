package com.yaloostore.front.common.controller;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import static org.springframework.http.HttpHeaders.COOKIE;

@Controller
public class HomeController {

    private final QuerydslOrderSystemService querydslOrderSystemService;


    @GetMapping("/")
    public String main(Model model,
                       @CookieValue(required = false, name = COOKIE)Cookie cookie,
                       HttpServletResponse response){
        model.addAttribute(
                "bestseller",
                querydslOrderSystemService.getBestSeller()
        );


        return "main/index";
    }

    @GetMapping("/mypage")
    public String mypage(Model model){
        //해당 회원의 point, 등급 가져오는 service 작성

        return "mypage/index";

    }
    @GetMapping("/manager")
    public String manager(){
        return "manager/index";

    }

}
