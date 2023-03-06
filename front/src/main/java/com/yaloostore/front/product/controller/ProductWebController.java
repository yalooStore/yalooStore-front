package com.yaloostore.front.product.controller;


import com.yaloostore.front.common.dto.request.PageRequestDto;
import com.yaloostore.front.common.dto.response.PaginationResponseDto;
import com.yaloostore.front.product.dto.response.ProductBookResponseDto;
import com.yaloostore.front.product.dto.response.ProductTypeResponseDto;
import com.yaloostore.front.product.service.inter.ProductTypeSystemService;
import com.yaloostore.front.product.service.inter.QuerydslProductSystemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductWebController {


    private final QuerydslProductSystemService querydslProductSystemService;
    private final ProductTypeSystemService productTypeSystemService;

    /**
     * 사용자용 전체 상품 조회 view 반환 컨트롤러
     * */
    @GetMapping
    public String products(@RequestParam(required = false) Integer typeId,
                           @RequestParam(required = false, defaultValue = "0") Integer page,
                           @RequestParam(required = false, defaultValue = "15") Integer size,
                           Model model){

        model.addAttribute("size", size);

        PaginationResponseDto<ProductBookResponseDto> products = querydslProductSystemService.findAllProducts(new PageRequestDto(page, size)
                , typeId);

        Map<String, Object> pageMap = getPage(products);
        model.addAllAttributes(pageMap);


        model.addAllAttributes(Map.of(
                "products", products.getDataList(),
                "typeId", Objects.isNull(typeId) ? "" :typeId,
                "types", productTypeSystemService.findAllProductType()
        ));

        return "main/product_all_list";
    }

    private Map<String, Object> getPage(PaginationResponseDto<ProductBookResponseDto> products) {

        return Map.of(
                "totalPage",products.getTotalPage(),
                "currentPage", products.getCurrentPage(),
                "totalDataCount",products.getTotalDataCount(),
                "dataList",products.getDataList()
        );


    }
}
