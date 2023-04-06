package com.yaloostore.front.product.service.inter;

import com.yaloostore.front.common.dto.request.PageRequestDto;
import com.yaloostore.front.common.dto.response.PaginationResponseDto;
import com.yaloostore.front.product.dto.response.ProductBookNewStockResponse;
import com.yaloostore.front.product.dto.response.ProductBookResponseDto;
import com.yaloostore.front.product.dto.response.ProductDetailViewResponse;

import java.util.List;

public interface QuerydslProductSystemService {

    /**
     * 최근 입고된 상품을 기준으로 도서 목록을 가져오는 메소드
     * */
    List<ProductBookNewStockResponse> findNewOneBookProduct();


    /**
     * 모든 상품(도서)를 가져오는 메소드입니다.
     * */
    PaginationResponseDto<ProductBookResponseDto> findAllProducts(PageRequestDto pageRequestDto,
                                                              Integer typeId);


    /**
     * 해당 상품의 아이디 값으로 상품 상세 정보를 가져옵니다.
     * */
    ProductDetailViewResponse findProductDetailByProductId(Long productId);


}
