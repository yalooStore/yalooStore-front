package com.yaloostore.front.common.controller;


import com.yaloostore.front.common.utils.CookieUtils;
import com.yaloostore.front.product.dto.response.ProductBookNewStockResponse;
import com.yaloostore.front.product.dto.response.ProductRecentResponseDto;
import com.yaloostore.front.product.service.inter.QuerydslProductSystemService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableDefault;
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
                       HttpServletResponse response,
                       HttpServletRequest request){

        long startTime = System.currentTimeMillis();
        List<ProductRecentResponseDto> newArriveProducts = querydslProductSystemService.findNewArriveProducts(PageRequest.of(0, 12));
        long endTime = System.currentTimeMillis();

        model.addAttribute("newArriveProducts", newArriveProducts);

        long startTime_notCache = System.currentTimeMillis();
        List<ProductBookNewStockResponse> newOneBookProduct = querydslProductSystemService.findNewOneBookProduct();
        long endTime_notCache = System.currentTimeMillis();
        model.addAttribute(
                "newOneBookProduct",
                querydslProductSystemService.findNewOneBookProduct()
        );

        log.info("api 호출 - 캐시 O : {}", endTime-startTime);
        log.info("api 호출 - 캐시 x : {}", endTime_notCache-startTime_notCache);

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
