package com.yaloostore.front.product.service.inter;

import com.yaloostore.front.common.dto.request.PageRequestDto;
import com.yaloostore.front.common.dto.response.PaginationResponseDto;
import com.yaloostore.front.product.dto.response.ProductBookNewOneResponse;
import com.yaloostore.front.product.dto.response.ProductBookResponseDto;
import org.springframework.data.domain.jaxb.SpringDataJaxb;

import java.util.List;

public interface QuerydslProductSystemService {

    /**
     * 최근 입고된 상품을 기준으로 도서 목록을 가져오는 메소드
     * */
    List<ProductBookNewOneResponse> findNewOneBookProduct();


    /**
     * 모든 상품(도서)를 가져오는 메소드입니다.
     *
     *
     * */
    PaginationResponseDto<ProductBookResponseDto> findAllProducts(PageRequestDto pageRequestDto,
                                                              Integer typeId);
}
