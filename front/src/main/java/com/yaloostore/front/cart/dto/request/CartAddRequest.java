package com.yaloostore.front.cart.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CartAddRequest {
    private String productId;
    private int quantity;
    private Boolean isEbook;

    // 저자 이름, 책 이름, 가격, 갯수, 썸네일,
}
