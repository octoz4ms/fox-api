package com.octo.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class ExcelUser {
    /**
     * 登录账号
     */
    @ExcelProperty(value = "登录账号")
    private String username;

    /**
     * 密码
     */
    @ExcelProperty(value = "登录密码")
    private String password;

    /**
     * 昵称
     */
    @ExcelProperty(value = "用户名")
    private String nickname;

    /**
     * 手机号
     */
    @ExcelProperty(value = "手机号")
    private String phone;

    /**
     * 邮箱
     */
    @ExcelProperty(value = "邮箱")
    private String email;

}
