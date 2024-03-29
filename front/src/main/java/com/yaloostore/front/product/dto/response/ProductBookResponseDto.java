package com.yaloostore.front.product.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductBookResponseDto {


    //Product
    private Long productId;
    private String productName;

    private Long stock;
    private LocalDateTime productCreatedAt;
    private String description;
    private String thumbnailUrl;
    private Long fixedPrice;
    private Long rawPrice;
    private Boolean isSelled;
    private Boolean isDeleted;
    private Boolean isExpose;
    private Long discountPercent;



    //book
    protected String publisherName;
    private String authorName;
    private String isbn;
    private Long pageCount;

    private LocalDateTime bookCreatedAt;

}
