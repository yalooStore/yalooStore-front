package com.yaloostore.front.product.service.impl;

import com.yalooStore.common_utils.dto.ResponseDto;
import com.yaloostore.front.config.GatewayConfig;
import com.yaloostore.front.product.dto.response.ProductTypeResponseDto;
import com.yaloostore.front.product.service.inter.ProductTypeSystemService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Getter
@RequiredArgsConstructor
@Service
public class ProductTypeSystemServiceImpl implements ProductTypeSystemService {


    private final RestTemplate restTemplate;
    private final String PATH ="/api/service/product-types";
    private final GatewayConfig gatewayConfig;


    /**
     * 상품 유형 전체 조회를 요청하고 받은 응답을 돌려줍니다.
     *
     * @return 응답 받은 전체 조회된 상품 유형 list dto 객체
     * */
    @Override
    public List<ProductTypeResponseDto> findAllProductType() {
        ResponseEntity<ResponseDto<List<ProductTypeResponseDto>>> response =
                restTemplate.exchange(
                        gatewayConfig.getShopUrl() + PATH,
                        HttpMethod.GET,
                        getHttpEntity(),
                        new ParameterizedTypeReference<ResponseDto<List<ProductTypeResponseDto>>>() {});

        return Objects.requireNonNull(response.getBody()).getData();
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
