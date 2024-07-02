package com.octo.exception;

import com.octo.util.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public ApiResponse<Object> handleException(Exception e) {
        // 处理异常并返回包含错误信息的响应体对象
        GlobalExceptionHandler.log.error(e.getMessage());
        return ApiResponse.fail(500, e.getMessage());
    }

    @ExceptionHandler(CustomException.class)
    public ApiResponse<Object> handleCustomException(CustomException ex) {
        GlobalExceptionHandler.log.error(ex.getMessage());
        return ApiResponse.fail(ex.getCode(), ex.getMessage());
    }
}
