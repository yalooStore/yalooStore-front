package com.yaloostore.front.common.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yalooStore.common_utils.dto.ResponseDto;
import com.yaloostore.front.common.exception.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;


/**
 * restTemplate을 사용해서 받아온 요청이 예외를 받아온다면 Http 상태 코드에 따라 예외를 발생시키기 위한 핸들러입니다.
 * */
@RequiredArgsConstructor
@Component
@Slf4j
public class CustomResponseErrorHandler implements ResponseErrorHandler {

    private final ObjectMapper objectMapper;

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {

        return response.getStatusCode().is5xxServerError() || response.getStatusCode().is4xxClientError();

    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {

        InputStream body = response.getBody();
        String messageBody = StreamUtils.copyToString(body, StandardCharsets.UTF_8);

        ResponseDto<Object> responseDto = objectMapper.readValue(messageBody, ResponseDto.class);

        int statusCode = response.getStatusCode().value();
        List<String> errorMessages = responseDto.getErrorMessages();

        if (statusCode == HttpStatus.UNAUTHORIZED.value()){
            throw new CustomUnauthorizedException(errorMessages.toString());
        } else if (statusCode == HttpStatus.NOT_FOUND.value()){
            throw new CustomNotFoundException(errorMessages.toString());
        } else if (statusCode == HttpStatus.BAD_REQUEST.value()) {
            throw new CustomBadRequestException(errorMessages.toString());
        } else if (statusCode == HttpStatus.METHOD_NOT_ALLOWED.value()) {
            throw new CustomMethodNotAllowedException(errorMessages.toString());
        } else if (statusCode == HttpStatus.CONFLICT.value()) {
            throw new CustomConflictException(errorMessages.toString());
        } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
            throw new CustomForbiddenException(errorMessages.toString());
        } else if (statusCode == HttpStatus.BAD_GATEWAY.value()) {
            throw new CustomGatewayException(errorMessages.toString());
        } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            throw new CustomServerException(errorMessages.toString());
        }

    }
}
