package com.yaloostore.front.product.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductBookNewOneResponse {

    private Long productId;
    private String productName;
    private String description;
    private String thumbnailUrl;
    private Long fixedPrice;
    private Long rawPrice;
    private Long discountPercent;
    private String isbn;
    private String publisherName;
    private String authorName;
}
