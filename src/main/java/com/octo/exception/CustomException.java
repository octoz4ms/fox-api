package com.octo.exception;

import com.octo.enums.ResponseCodeEnums;

public class CustomException extends RuntimeException {
    private int code;
    private String message;

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

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
