package com.octo.dto.request;

import lombok.Data;

/**
 * @author zms
 */
@Data
public class LoginReq {
    private String username;
    private String password;

    private String code;

    private String uuid;
}
