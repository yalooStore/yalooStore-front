package com.yaloostore.front.product.service.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yalooStore.common_utils.dto.ResponseDto;
import com.yaloostore.front.common.dto.request.PageRequestDto;
import com.yaloostore.front.common.dto.response.PaginationResponseDto;
import com.yaloostore.front.product.dto.response.ProductBookNewStockResponse;
import com.yaloostore.front.product.dto.response.ProductBookResponseDto;
import com.yaloostore.front.product.dto.response.ProductDetailViewResponse;
import com.yaloostore.front.product.dto.response.ProductRecentResponseDto;
import com.yaloostore.front.product.service.inter.QuerydslProductSystemService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
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
    public List<ProductBookNewStockResponse> findNewOneBookProduct() {

        URI uri = UriComponentsBuilder
                .fromUriString(shopUrl)
                .path(PATH + "/new/stock/book")
                .encode()
                .build()
                .toUri();

        ResponseEntity<List<ProductBookNewStockResponse>> responseEntity=
                restTemplate.exchange(uri,
                        HttpMethod.GET,
                        getHttpEntity(),
                        new ParameterizedTypeReference<List<ProductBookNewStockResponse>>() {});


        List<ProductBookNewStockResponse> newOneResponseList = responseEntity.getBody();
        return Objects.requireNonNull(newOneResponseList);
    }

    @Override
    public List<ProductRecentResponseDto> findNewArriveProducts(Pageable pageable) {

        String url = UriComponentsBuilder.fromUriString(shopUrl)
                .path(PATH + "/new-arrivals")
                .queryParam("size", pageable.getPageSize())
                .queryParam("page", pageable.getPageNumber())
                .toUriString();

        ResponseEntity<ResponseDto<List<ProductRecentResponseDto>>> response =
                restTemplate.exchange(url,
                        HttpMethod.GET,
                        getHttpEntity(),
                        new ParameterizedTypeReference<>() {});
        return Objects.requireNonNull(response.getBody().getData());

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
        ResponseEntity<ResponseDto<PaginationResponseDto<ProductBookResponseDto>>> products = restTemplate.exchange(uri, HttpMethod.GET, getHttpEntity(),
                new ParameterizedTypeReference<>(){});

        return Objects.requireNonNull(products.getBody()).getData();
    }


    /**
     * {@inheritDoc}
     * */
    @Override
    public ProductDetailViewResponse findProductDetailByProductId(Long productId) {

        URI uri = UriComponentsBuilder
                .fromUriString(shopUrl)
                .path(PATH + "/" + productId)
                .encode()
                .build()
                .toUri();

        ResponseEntity<ResponseDto<ProductDetailViewResponse>> detailView =
                restTemplate.exchange(uri,
                        HttpMethod.GET,
                        getHttpEntity(),
                        new ParameterizedTypeReference<ResponseDto<ProductDetailViewResponse>>() {
                        });

        return Objects.requireNonNull(detailView.getBody().getData());
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
