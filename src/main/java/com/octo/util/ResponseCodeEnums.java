package com.octo.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zms
 */
@AllArgsConstructor
@Getter
public enum ResponseCodeEnums {
    SUCCESS(0, "操作成功"),
    FAIL(500, "操作失败"),
    HTTP_STATUS_200(200, "ok"),
    HTTP_STATUS_400(400, "request error"),
    HTTP_STATUS_401(401, "no authentication"),
    HTTP_STATUS_403(403, "no authorities"),
    HTTP_STATUS_500(500, "server error");

    private final int code;
    private final String message;
}
