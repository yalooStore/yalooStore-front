package com.yaloostore.front.common.controller;


import com.yaloostore.front.common.utils.CookieUtils;
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
import static org.springframework.http.HttpHeaders.COOKIE;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {


    private final CookieUtils cookieUtils;

    private final QuerydslProductSystemService querydslProductSystemService;


    //TODO: 해당 쿠키 네임이 COOKIE인게 맞는지 AUTH 관련 로직 살펴볼것.
    @GetMapping("/")
    public String main(Model model,
                       @CookieValue(required = false, name = COOKIE)Cookie cookie,
                       HttpServletResponse response, HttpServletRequest request){

        String uuid = cookieUtils.getUuidFromCookie(request.getCookies(), HEADER_UUID.getValue());
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Object credentials = SecurityContextHolder.getContext().getAuthentication().getCredentials();


        log.info("name!!!!!!!! : {}", name);
        log.info("token !!  : {} ", credentials.toString());

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
