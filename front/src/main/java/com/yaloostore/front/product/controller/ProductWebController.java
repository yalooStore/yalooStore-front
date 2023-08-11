package com.yaloostore.front.product.controller;


import com.yaloostore.front.common.dto.request.PageRequestDto;
import com.yaloostore.front.common.dto.response.PaginationResponseDto;
import com.yaloostore.front.product.dto.response.ProductBookResponseDto;
import com.yaloostore.front.product.dto.response.ProductDetailViewResponse;
import com.yaloostore.front.product.dto.response.ProductTypeResponseDto;
import com.yaloostore.front.product.service.inter.ProductTypeSystemService;
import com.yaloostore.front.product.service.inter.QuerydslProductSystemService;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
@Slf4j
public class ProductWebController {

    private final QuerydslProductSystemService querydslProductSystemService;


    /**
     * 사용자용 전체 상품 조회 view 반환 컨트롤러
     * */
    @GetMapping
    public String products(@RequestParam(required = false, name = "typeId") Integer typeId,
                           @RequestParam(required = false, defaultValue = "0") Integer page,
                           @RequestParam(required = false, defaultValue = "15") Integer size,
                           Model model){

        model.addAttribute("size", size);

        PaginationResponseDto<ProductBookResponseDto> products = querydslProductSystemService.findAllProducts(new PageRequestDto(page, size)
                , typeId);

        Map<String, Object> pageMap = getPage(products);
        model.addAllAttributes(pageMap);
        List<ProductTypeResponseDto> allProductType = productTypeSystemService.findAllProductType();

        model.addAllAttributes(Map.of(
                "products", products.getDataList(),
                "typeId", Objects.isNull(typeId) ? "" :typeId,
                "types", productTypeSystemService.findAllProductType()
        ));

        return "main/product/products";
    }

    private final ProductTypeSystemService productTypeSystemService;

    private Map<String, Object> getPage(PaginationResponseDto<ProductBookResponseDto> products) {

        return Map.of(
                "totalPage",products.getTotalPage(),
                "currentPage", products.getCurrentPage(),
                "totalDataCount",products.getTotalDataCount(),
                "dataList",products.getDataList()
        );


    }


    @GetMapping("/{productId}")
    public String getProductDetail(@PathVariable("productId") Long productId,
                                   Model model){

        ProductDetailViewResponse response = querydslProductSystemService
                .findProductDetailByProductId(productId);
        model.addAttribute("productDetail", response);
        return "main/product/productDetail";

    }
}
