package com.yaloostore.front.common.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;


@Slf4j
public class RequestLoggingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception {
        log.info("x-forwarded-for {}", request.getHeader("x-forwarded-for"));
        log.info(
                "[client-ip]: {} | [request-url]: {}",
                request.getHeader("x-forwarded-for"),
                request.getServletPath()
        );

        return true;
    }
}