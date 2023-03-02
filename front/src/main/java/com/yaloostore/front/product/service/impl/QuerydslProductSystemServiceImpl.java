package com.yaloostore.front.product.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yaloostore.front.product.dto.response.ProductBookNewOneResponse;
import com.yaloostore.front.product.service.inter.QuerydslProductSystemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

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
    public List<ProductBookNewOneResponse> getNewOneBookProduct() {

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

    private HttpEntity getHttpEntity(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        return new HttpEntity(httpHeaders);

    }
}
