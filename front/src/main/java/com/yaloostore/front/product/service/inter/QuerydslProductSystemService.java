package com.yaloostore.front.product.service.inter;

import com.yaloostore.front.product.dto.response.ProductBookNewOneResponse;

import java.util.List;

public interface QuerydslProductSystemService {

    /**
     * 최근 입고된 상품을 기준으로 도서 목록을 가져오는 메소드
     * */
    List<ProductBookNewOneResponse> getNewOneBookProduct();
}
