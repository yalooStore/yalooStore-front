package com.yaloostore.front.product.dto.response;


import lombok.*;
@Getter
public class ProductDetailViewResponse {

    private Long productId;
    private String productName;
    private String thumbnail;
    private Long rawPrice;
    private Long discountPrice;
    private Long discountPercent;
    private Boolean isSold;
    private Long quantity;
    private String description;

    //book
    private String isbn;
    private Long pageCnt;
    private String publisherName;
    private String authorName;

}
