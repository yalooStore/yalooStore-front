package com.yaloostore.front.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 오브젝트 매퍼를 빈으로 등록 후 사용하기 위한 설정 클래스
 * */
@Configuration
public class ObjectMapperConfig {

    @Bean
    public ObjectMapper objectMapper(){

        ObjectMapper mapper = new ObjectMapper();

        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        mapper.registerModule(new JavaTimeModule());

        return mapper;
    }
}
