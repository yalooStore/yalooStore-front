package com.yaloostore.front.product.controller;


import com.yaloostore.front.common.dto.response.PaginationResponseDto;
import com.yaloostore.front.product.dto.request.SearchRequest;
import com.yaloostore.front.product.dto.response.SearchProductResponseDto;
import com.yaloostore.front.product.service.inter.SearchProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/search/products")
public class SearchProductWebController {

    private final SearchProductService service;


    @GetMapping
    public String searchProducts(
            Model model,
            @ModelAttribute SearchRequest request,
            @PageableDefault Pageable pageable
    ){

        PaginationResponseDto<SearchProductResponseDto> response = service.searchProductsBySearchType(request, pageable);

        model.addAttribute("totalPage", response.getTotalPage());
        model.addAttribute("currentPage", response.getCurrentPage());
        model.addAttribute("totalDataCount", response.getTotalDataCount());
        model.addAttribute("products", response.getDataList());
        model.addAttribute("url", "/search/products");

        model.addAttribute("searchType", request.getSearchType());
        model.addAttribute("searchContent", request.getSearchContent());
        model.addAttribute("size",pageable.getPageSize());

        return "/main/product/searchProducts";

    }
}
