package com.yaloostore.front.product.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yalooStore.common_utils.dto.ResponseDto;
import com.yaloostore.front.common.dto.request.PageRequestDto;
import com.yaloostore.front.common.dto.response.PaginationResponseDto;
import com.yaloostore.front.product.dto.response.ProductBookNewOneResponse;
import com.yaloostore.front.product.dto.response.ProductBookResponseDto;
import com.yaloostore.front.product.service.inter.QuerydslProductSystemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.Type;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class QuerydslProductSystemServiceImpl implements QuerydslProductSystemService {

    private final String PATH = "/api/service/products";
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${shop.server.url}")
    private String shopUrl;


    /**
     * {@inheritDoc}
     * */
    @Override
    public List<ProductBookNewOneResponse> findNewOneBookProduct() {

        URI uri = UriComponentsBuilder
                .fromUriString(shopUrl)
                .path(PATH + "/new/stock/book")
                .encode()
                .build()
                .toUri();

        ResponseEntity<List<ProductBookNewOneResponse>> responseEntity=
                restTemplate.exchange(uri,
                        HttpMethod.GET,
                        getHttpEntity(),
                        new ParameterizedTypeReference<List<ProductBookNewOneResponse>>() {});


        List<ProductBookNewOneResponse> newOneResponseList = responseEntity.getBody();
        return Objects.requireNonNull(newOneResponseList);
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public PaginationResponseDto<ProductBookResponseDto> findAllProducts(PageRequestDto pageRequestDto, Integer typeId) {
        URI uri = UriComponentsBuilder.fromUriString(shopUrl)
                .path(PATH+"/books")
                .queryParam("typeId", typeId)
                .queryParam("page", pageRequestDto.getPage())
                .queryParam("size", pageRequestDto.getSize())
                .encode()
                .build()
                .toUri();
        ResponseEntity<ResponseDto<PaginationResponseDto<ProductBookResponseDto>>> products = restTemplate.exchange(uri, HttpMethod.GET, getHttpEntity(), new ParameterizedTypeReference<ResponseDto<PaginationResponseDto<ProductBookResponseDto>>>(){});

        return Objects.requireNonNull(products.getBody()).getData();
    }

    /**
     * restTemplate에서 HttpEntity 사용이 중복됨에 따라서 메소드로 추출해서 사용할 수 있도록 한다.
     * */
    private HttpEntity getHttpEntity(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        return new HttpEntity(httpHeaders);

    }
}
