package com.yaloostore.front.common.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(){
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
