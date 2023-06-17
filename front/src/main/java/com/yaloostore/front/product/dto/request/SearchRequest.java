package com.yaloostore.front.product.dto.request;


import com.fasterxml.jackson.annotation.JsonGetter;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SearchRequest {

    private String searchType;
    private String searchContent;


}
