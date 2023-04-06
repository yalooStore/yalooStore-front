package com.yaloostore.front.member.exception;

import org.springframework.validation.BindingResult;

public class ValidationFailedException extends RuntimeException {
    public ValidationFailedException(BindingResult bindingResult) {
        super("validation exception");
    }
}
