package com.yaloostore.front.cart.controller.web;


import com.yalooStore.common_utils.dto.ResponseDto;
import com.yaloostore.front.auth.utils.CookieUtils;
import com.yaloostore.front.cart.dto.response.CartViewResponse;
import com.yaloostore.front.config.GatewayConfig;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import org.springframework.http.HttpHeaders;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
@Slf4j
public class CartWebController {


    private final RedisTemplate<String, Object> redisTemplate;

    private final RestTemplate restTemplate;
    private final GatewayConfig gatewayConfig;
    private final CookieUtils cookieUtils;


    /**
     * 회원, 비회원이 담아둔 장바구니 view를 반환합니다.
     * <p>
     * cookie
     * - CART_NO가 있을 때 = 장바구니에 넣은 물건이 존재할 때, 회원이 로그인을 했을 때
     * - CART_NO이 없을 때 = 장바구니에 넣은 물건이 없을 때
     */
    @GetMapping
    public String cart(
            @CookieValue(value = "CART_NO", required = false) Cookie cookie,
            @CookieValue(value = "AUTH_MEMBER", required = false) Cookie member,
            Model model,
            HttpServletResponse httpServletResponse
    ) {

        List<CartViewResponse> ebookCart = new ArrayList<>();
        List<CartViewResponse> deliveryCart = new ArrayList<>();


        //회원이 해당 쿠키 삭제를 할 경우 다시 쿠키 생성해서 넣어준다 ~
        if (Objects.nonNull(member)) {
            String loginId = SecurityContextHolder.getContext().getAuthentication().getName();
            cookie = cookieUtils.createCookie("CART_NO", loginId, 60 * 60 * 24 * 30);

            log.info("cookie : {}", cookie);
            httpServletResponse.addCookie(cookie);

        }


        //CART_NO 쿠키 존재 x -> 장바구니에 넣은 물건이 없는 경우
        //CART_NO 쿠키 존재 -> 장바구니에 넣은 물건이 있는 경우, 회원 로그인을 한 경우(위에서 member 관련 쿠키가 있는지 확인해주어서 가능)
        if (Objects.nonNull(cookie)) {
            if (cookie.getName().equals("anonymousUser")) {
                return "main/cart/cart";
            }


            //장바구니에서 가져온 데이터를 사용해서 key, value1....n개를 저장해줌
            Map<Object, Object> cart = redisTemplate.opsForHash().entries(cookie.getValue());
            log.info("cart : {}", cart);

            MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
            cart.keySet().forEach(key -> {
                multiValueMap.add(key.toString(), cart.get(key).toString());
            });


            HttpHeaders headers = new HttpHeaders();
            HttpEntity<String> entity = new HttpEntity<>(headers);

            UriComponents uri = UriComponentsBuilder.fromHttpUrl(gatewayConfig.getShopUrl())
                    .path("/api/service/products/cart").queryParams(multiValueMap).build();
            log.info("uri123 : {} ", uri);


            ResponseDto<List<CartViewResponse>> response = restTemplate.exchange(uri.toUri(),
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<ResponseDto<List<CartViewResponse>>>() {
                    }).getBody();

            log.info("response123213: {}", response.getData());

            Objects.requireNonNull(Objects.requireNonNull(response).getData()).forEach(product -> {
                if (Boolean.FALSE.equals(product.getIsDeleted())) {
                    if (Boolean.TRUE.equals(product.getIsEbook())) {
                        ebookCart.add(product);
                    } else {
                        deliveryCart.add(product);
                    }
                }
            });
        }
        model.addAttribute("ebookCart", ebookCart);
        log.info("deliveryCart : {}", deliveryCart);
        model.addAttribute("deliveryCart", deliveryCart);

        return "main/cart/cart";

    }

}

