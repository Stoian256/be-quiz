package com.example.bequiz.exception;

import lombok.Builder;
import lombok.Getter;

import static java.lang.String.format;

@Builder
@Getter
public class EntityValidationException extends RuntimeException {
    private final ErrorCode errorCode;

    public EntityValidationException(ErrorCode code) {
        super(code.getMessage());
        this.errorCode = code;
    }

    public EntityValidationException(ErrorCode code, String param) {
        super(format(code.getMessage(), param));
        this.errorCode = code;
    }
}
