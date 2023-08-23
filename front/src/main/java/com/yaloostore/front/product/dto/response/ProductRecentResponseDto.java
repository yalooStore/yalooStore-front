package com.yaloostore.front.product.dto.response;

import lombok.*;


/**
 * 사용자가 최근 확인한 상품과 관련한 응답에 사용하는 response dto 입니다.
 * */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Builder
public class ProductRecentResponseDto {
    private Long productId;
    private String productName;
    private String thumbnailUrl;
    private Long stock;
    //할인된 가격
    private Long discountPrice;
    //정가
    private Long rawPrice;
    private Boolean isSold; //이걸 사용해서 다팔린건 회색으로 ..
    private Boolean forcedOutOfStock;
    private Boolean isEbook;
    private String publisherName;
    private String authorName;


}