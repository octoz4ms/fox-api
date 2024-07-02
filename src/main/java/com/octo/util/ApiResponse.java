package com.octo.util;

import com.octo.enums.ResponseCodeEnums;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T> {
    private int code;
    private String message;
    private T data;

    public ApiResponse(int code, String msg) {
        this.code = code;
        message = msg;
    }

    public ApiResponse(T data) {
        this.data = data;
        code = ResponseCodeEnums.SUCCESS.getCode();
        message = ResponseCodeEnums.SUCCESS.getMessage();
    }


    // 静态工具方法
    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>(ResponseCodeEnums.SUCCESS.getCode(), ResponseCodeEnums.SUCCESS.getMessage());
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(ResponseCodeEnums.SUCCESS.getCode(), ResponseCodeEnums.SUCCESS.getMessage(), data);
    }

    public static <T> ApiResponse<T> fail() {
        return new ApiResponse<>(ResponseCodeEnums.FAIL.getCode(), ResponseCodeEnums.FAIL.getMessage());
    }

    public static <T> ApiResponse<T> fail(String message) {
        return new ApiResponse<>(ResponseCodeEnums.FAIL.getCode(), message);
    }

    public static <T> ApiResponse<T> fail(int code, String message) {
        return new ApiResponse<>(code, message);
    }

    public static <T> ApiResponse<T> fail(ResponseCodeEnums responseCodeEnums) {
        return new ApiResponse<>(responseCodeEnums.getCode(), responseCodeEnums.getMessage());
    }
}
