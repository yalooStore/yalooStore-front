package com.yaloostore.front.common.controller;


import com.yaloostore.front.auth.utils.CookieUtils;
import com.yaloostore.front.product.dto.response.ProductBookNewStockResponse;
import com.yaloostore.front.product.service.inter.QuerydslProductSystemService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import static com.yaloostore.front.auth.utils.AuthUtil.HEADER_UUID;
import static com.yaloostore.front.auth.utils.AuthUtil.JWT;
import static org.springframework.http.HttpHeaders.COOKIE;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    //private final QuerydslOrderSystemService querydslOrderSystemService;

    private final CookieUtils cookieUtils;

    private final QuerydslProductSystemService querydslProductSystemService;

    @GetMapping("/")
    public String main(Model model,
                       @CookieValue(required = false, name = COOKIE)Cookie cookie,
                       HttpServletResponse response, HttpServletRequest request){

        String uuid = cookieUtils.getUuidFromCookie(request.getCookies(), HEADER_UUID.getValue());


        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        log.info("name!!!!!!!! : {}", name);

        List<ProductBookNewStockResponse> newOneBookProduct = querydslProductSystemService.findNewOneBookProduct();
        model.addAttribute(
                "newOneBookProduct",
                querydslProductSystemService.findNewOneBookProduct()
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
