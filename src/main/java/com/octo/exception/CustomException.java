package com.octo.exception;

import com.octo.enums.ResponseCodeEnums;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final int code;
    private final String message;

    public CustomException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public CustomException(ResponseCodeEnums codeEnums) {
        super(codeEnums.getMessage());
        code = codeEnums.getCode();
        message = codeEnums.getMessage();
    }

}
