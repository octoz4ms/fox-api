package com.octo.util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public ApiResponse<Object> handleException(Exception e) {
        // 处理异常并返回包含错误信息的响应体对象
        log.error(e.getMessage());
        return ApiResponse.error(500, e.getMessage());
    }
}
