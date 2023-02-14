package com.yaloostore.front.common.controller;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Objects;


/**
 * 예외 발생 시 예외 페이지로 뷰페이지를 보내줄 컨틀롤러 입니다.
 * 각 컨트롤러는 예외 발생 시 http 상태 코드에 따라서 예외 페이지를 구분하여 반환합니다.
 * */
@Controller
public class ErrorPageController implements ErrorController {

    @GetMapping("/error")
    public String handleError(Model model,
                              HttpServletRequest request){


        Object statusCode = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if(Objects.nonNull(statusCode)){
            int code = Integer.parseInt(statusCode.toString());

            if(Objects.equals(code, HttpStatus.FORBIDDEN.value())){
                return "common/errors/forbidden";
            } else if (Objects.equals(code, HttpStatus.NOT_FOUND.value())){
                return "common/errors/4xx";

            } else if (Objects.equals(code, HttpStatus.INTERNAL_SERVER_ERROR.value())){
                return "common/errors/5xx";
            }
        }
        return "common/errors/error";
    }
}
