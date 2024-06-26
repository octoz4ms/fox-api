package com.octo.util;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zms
 */
@Data
public class Response<T> implements Serializable {
    private Integer code;
    private String message;
    private T data;

    public Response(int code, String msg) {
        this.code = code;
        message = msg;
    }

    public Response(T data) {
        this.data = data;
        code = ResponseCodeEnums.SUCCESS.getCode();
        message = ResponseCodeEnums.SUCCESS.getMessage();
    }

    public Response() {
        code = ResponseCodeEnums.SUCCESS.getCode();
        message = ResponseCodeEnums.SUCCESS.getMessage();
    }

    public Response(ResponseCodeEnums statusEnums) {
        code = statusEnums.getCode();
        message = statusEnums.getMessage();
    }

    public Response(T data, String msg) {
        this.data = data;
        code = ResponseCodeEnums.SUCCESS.getCode();
        message = msg;
    }


    public static <T> Response<T> success(T data) {
        return new Response<>(data);
    }

    public static <T> Response<T> success() {
        return new Response<>();
    }

    public static <T> Response<T> fail(String message) {
        return new Response<>(ResponseCodeEnums.FAIL.getCode(), message);
    }

    public static <T> Response<T> fail() {
        return new Response<>(ResponseCodeEnums.FAIL.getCode(), ResponseCodeEnums.FAIL.getMessage());
    }
}

