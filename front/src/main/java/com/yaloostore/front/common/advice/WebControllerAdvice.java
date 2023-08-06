package com.yaloostore.front.common.advice;


import com.yaloostore.front.auth.exception.InvalidHttpHeaderException;
import com.yaloostore.front.common.exception.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Map;
import java.util.Objects;


/**
 * 에러를 공통으로 처리하기 위해 사용되는 어드바이스 클래스입니다.
 * */
@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class WebControllerAdvice {
    private final RedisTemplate redisTemplate;

    @ExceptionHandler(CustomNotFoundException.class)
    public String handlerNotFound(Exception e, Model model){

        model.addAttribute("error", e.getMessage());
        return "common/errors/not-found";
    }
    @ExceptionHandler(CustomBadRequestException.class)
    public String handlerBadRequest(Exception e, Model model){
        model.addAttribute("error", e.getMessage());
        return "common/errors/bad-request";
    }
    @ExceptionHandler(CustomMethodNotAllowedException.class)
    public String handlerMethodNotAllowed(Exception e, Model model){
        model.addAttribute("error", e.getMessage());
        return "common/errors/method-not-allowed";
    }
    @ExceptionHandler(CustomConflictException.class)
    public String handlerConflict(Exception e, Model model){
        model.addAttribute("error", e.getMessage());
        return "common/errors/conflict";
    }
    @ExceptionHandler({BadCredentialsException.class, CustomForbiddenException.class})
    public String handlerForbidden(Exception e, Model model){
        model.addAttribute("error", e.getMessage());
        return "common/errors/forbidden";
    }
    @ExceptionHandler(CustomUnauthorizedException.class)
    public String handlerUnauthorized(Exception e, Model model,
                                      HttpServletRequest request, HttpServletResponse response){

        HttpSession session = request.getSession();
        session.invalidate();
        SecurityContext context = SecurityContextHolder.getContext();
        SecurityContextHolder.clearContext();
        context.setAuthentication(null);

        model.addAttribute("error", e.getMessage());
        return "redirect:/members/login";
    }

    @ExceptionHandler({CustomServerException.class, InvalidHttpHeaderException.class})
    public String handlerServerException(Exception e, Model model){
        model.addAttribute("error", e.getMessage());
        return "common/errors/5xx";
    }
    @ExceptionHandler(Exception.class)
    public String handlerException(Exception e, Model model){
        model.addAttribute("error", e.getMessage());
        return "common/errors/error";
    }


    @ModelAttribute(name = "cartProductCounting")
    public int setViewHeaderFrag(@CookieValue(required = false, value = "CART_NO") Cookie cartNo){
        //장바구니가 비어 있는 경우라면?
        if (Objects.isNull(cartNo)){
            return 0;
        }

        if (Objects.nonNull(cartNo)){
            String uuid = cartNo.getValue();
            log.info("uuid = {}", uuid);

            Map<String, String> o = redisTemplate.opsForHash().entries(uuid);
            log.info("size??!?! {}", o.size());
            return o.size();
        }
        return 0;
    }



}
