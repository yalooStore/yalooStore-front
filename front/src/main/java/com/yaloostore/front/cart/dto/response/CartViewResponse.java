package com.yaloostore.front.cart.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartViewResponse {

    private Long productId;
    private Long quantity;
    private String thumbnailUrl;
    private String productName;
    private Long discountPrice;
    private Long rawPrice;
    private Long discountPercent;
    private Long savedPoint;


    private Boolean forcedOutOfStock;
    private Boolean isSale;
    private Boolean isDeleted;
    private Boolean isEbook;



}
