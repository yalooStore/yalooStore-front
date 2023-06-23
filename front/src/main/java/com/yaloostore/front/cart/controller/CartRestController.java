package com.yaloostore.front.cart.controller;


import com.yalooStore.common_utils.dto.ResponseDto;
import com.yaloostore.front.auth.utils.CookieUtils;
import com.yaloostore.front.cart.dto.request.CartAddRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@Slf4j
public class CartRestController {
    private final RedisTemplate<String, Object> redisTemplate;
    private final CookieUtils cookieUtils;


    /**
     * [POST /cart]
     * 장바구니에 상품을 추가하는 Post 메소드입니다.
     *
     * @param request 장바구니에 추가할 상품의 id, 개수, 상품 종류를 담은 dto 객체
     * @param cookie redis의 key값을 가진 CART_NO 쿠키
     * @param member 회원 유무를 구분하기 위한 쿠키(회원일 경우 AUTH_MEMBER 쿠키 존재), 회원용 장바구니 생성에 사용되는 쿠키
     * @param response 쿠키 정보를 추가하기 위한 HttpServletResponse 객체
     * @return 응답
     * */
    @PostMapping
    public ResponseDto<String> addToCart(@RequestBody CartAddRequest request,
                                   @CookieValue(value = "CART_NO", required = false) Cookie cookie,
                                   @CookieValue(value = "AUTH_MEMBER", required = false) Cookie member,
                                   HttpServletResponse response){

        //1개 이하의 수량을 담을 경우 에러를 던진다.
        if (request.getQuantity() < 1){
            return ResponseDto.<String>builder()
                    .success(false)
                    .status(HttpStatus.BAD_REQUEST)
                    .data("수량은 1개 이상 담아주세요")
                    .build();
        }

        //비회원이 이북을 구매하려 할 땐 에러를 던진다.
        if (Objects.isNull(member) && request.getIsEbook()){
            return ResponseDto.<String>builder()
                    .success(false)
                    .status(HttpStatus.UNAUTHORIZED)
                    .data("E-Book 구입을 위해서 로그인이 필요합니다")
                    .build();
        }
        //회원 로그인 상태에서 회원이 악의적으로 해당 쿠키를 삭제할 경우 쿠키 재생성
        if(Objects.nonNull(member)){
            String loginId = SecurityContextHolder.getContext().getAuthentication().getName();
            cookie = cookieUtils.createCookie("CART_NO", loginId,60*60*24*30);

            response.addCookie(cookie);
        }


        // 비회원인 경우 비회원용 장바구니 UUID를 발급해서 넣어준다.
        if(Objects.isNull(cookie)){
            String uuid = String.valueOf(UUID.randomUUID());
            cookie = cookieUtils.createCookie("CART_NO", uuid,  60 * 60 * 24 * 3);

            response.addCookie(cookie);
        }

        //anonymousUser 차단
        if(cookie.getName().equals("anonymousUser")){
            cookie.setMaxAge(0);
            response.addCookie(cookie);

            return ResponseDto.<String>builder()
                    .success(false)
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .data("다시 시도해주세요.")
                    .build();
        }


        Object preQuantity = redisTemplate.opsForHash().get(cookie.getValue(), request.getProductId());
        log.info("preQuantity: {}", preQuantity);


        // 회원이 E-Book을 1개 이상 담으려고 하는 경우 에러를 던진다. (회원 확인이 필요 없는 이유는 회원이 아니면 이북을 담아둘 수 없음)
        if (Objects.nonNull(preQuantity) && Integer.parseInt(preQuantity.toString()) == 1 && request.getIsEbook()){
            return ResponseDto.<String>builder()
                    .success(false)
                    .status(HttpStatus.BAD_REQUEST)
                    .data("해당 E-Book 상품은 회원당 1개 이상 담을 수 없습니다.")
                    .build();
        }


        //해당 상품이 E-BOOK이 아니고 이미 장바구니에 존재하는 경우 수량을 더해준다.
        int quantity = request.getQuantity();
        if (Boolean.TRUE.equals(Objects.nonNull(preQuantity) && !request.getIsEbook())){
            quantity += Integer.parseInt(preQuantity.toString());
        }

        redisTemplate.opsForHash().put(cookie.getValue(), request.getProductId(), quantity);


        //회원, 비회원 구분 없이 7일 뒤에 장바구니에 담아둔 상품 사라지게 설정
        redisTemplate.expire(cookie.getValue(), 7, TimeUnit.DAYS);


        return ResponseDto.<String>builder()
                .success(true)
                .status(HttpStatus.OK)
                .data("장바구니에 담기 완료")
                .build();
    }


    /**
     * [Post /cart/delete/{productId}] 장바구니 내의 상품 삭제를 위한 메소드
     *
     * @param cookie redis key값을 가진 CART_NO 쿸;
     * @param productId 삭제할 상품 id
     * */
    @PostMapping("/delete/{productId}")
    @ResponseBody
    public void delete(
            @PathVariable(name = "productId") String productId,
            @CookieValue("CART_NO") Cookie cookie){

        String key = cookie.getValue();

        log.info("cookie :{}", cookie.getValue());
        redisTemplate.opsForHash().delete(key, productId);

    }

    /**
     * [Post /cart/modify-quantity/{productId}] 장바구니 내 수량 변경을 위한 메소드
     *
     * @param productId 수량 변경을 하려는 상품 Id
     * @param quantity 장바구니 상품 개수
     * @param cookie redis Key 값을 가진 CART_NO 쿠키
     * */
    @PostMapping("/modify-quantity/{productId}")
    @ResponseBody
    public void modifyQuantity(@PathVariable(name = "productId") String productId,
                               @RequestBody Map<String, String> quantity,
                               @CookieValue("CART_NO") Cookie cookie){

        log.info("productId: {}", productId);
        log.info("cookie value : {}", cookie.getValue());

        String key = cookie.getValue();
        redisTemplate.opsForHash().put(key, productId, quantity.get("quantity"));
    }
}

