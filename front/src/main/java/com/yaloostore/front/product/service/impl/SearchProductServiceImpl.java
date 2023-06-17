package com.yaloostore.front.product.service.impl;


import com.yalooStore.common_utils.dto.ResponseDto;
import com.yaloostore.front.common.dto.response.PaginationResponseDto;
import com.yaloostore.front.product.dto.request.SearchRequest;
import com.yaloostore.front.product.dto.response.SearchProductResponseDto;
import com.yaloostore.front.product.service.inter.SearchProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Objects;


@RequiredArgsConstructor
@Service
public class SearchProductServiceImpl implements SearchProductService {

    private final RestTemplate restTemplate;
    @Value("${shop.server.url}")
    private String shopUrl;

    private static final String PATH = "/api/service/products/search";


    /**
     * TODO: 해당 부분은 여러 검색 기능이 있을 때 select로 선택 받아서 넘기는 param으로 여러 메소드를 동시 처리할 수 있게 하자
     * */
    @Override
    public PaginationResponseDto<SearchProductResponseDto> searchProductsBySearchType(SearchRequest request, Pageable pageable) {

        UriComponents uri = UriComponentsBuilder.fromHttpUrl(shopUrl)
                .path(PATH)
                .queryParam(request.getSearchType(), request.getSearchContent())
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize()).build();

        ResponseEntity<ResponseDto<PaginationResponseDto<SearchProductResponseDto>>> response =
                restTemplate.exchange(uri.toUriString(),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<ResponseDto<PaginationResponseDto<SearchProductResponseDto>>>() {
                        });


        return Objects.requireNonNull(response.getBody()).getData();
    }
}
