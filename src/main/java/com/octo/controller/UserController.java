package com.octo.controller;


import com.octo.entity.User;
import com.octo.util.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author zms
 * @since 2024-07-02
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/{id}")
    public ApiResponse<User> getUser(@PathVariable("id") int id) {
        return ApiResponse.success();
    }

}
