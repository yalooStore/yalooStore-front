package com.yaloostore.front.product.service.inter;

import com.yaloostore.front.common.dto.response.PaginationResponseDto;
import com.yaloostore.front.product.dto.request.SearchRequest;
import com.yaloostore.front.product.dto.response.SearchProductResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


public interface SearchProductService {

    PaginationResponseDto<SearchProductResponseDto> searchProductsBySearchType(SearchRequest request, Pageable pageable);



}
