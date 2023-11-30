package com.octo.util;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zms
 */
@Data
public class Response<T> implements Serializable {
    private Integer code;
    private String msg;
    private T data;

    public static <T> Response<T> success(T data) {
        return new Response<>(data);
    }

    public static <T> Response<T> fail(String message) {
        return new Response<>(ResponseCodeEnums.FAIL.getCode(), message);
    }


    public Response(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Response(T data) {
        this.data = data;
        code = ResponseCodeEnums.SUCCESS.getCode();
        msg = ResponseCodeEnums.SUCCESS.getMsg();
    }
}

//    public static <T> Response<T> success() {
//        return new Response<>();
//    }
//
//    public Response() {
//        code = ResponseCodeEnums.SUCCESS.getCode();
//        msg = ResponseCodeEnums.SUCCESS.getMessage();
//    }
//
//    public Response(ResponseCodeEnums statusEnums) {
//        code = statusEnums.getCode();
//        msg = statusEnums.getMessage();
//    }
//
//    public Response(T data, String msg) {
//        this.data = data;
//        code = ResponseCodeEnums.SUCCESS.getCode();
//        this.msg = msg;
//    }

