package com.octo.controller;

import com.octo.dto.request.LoginReq;
import com.octo.entity.User;
import com.octo.service.ILoginService;
import com.octo.service.IUserService;
import com.octo.util.ApiResponse;
import com.octo.util.JwtUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@RestController
@RequestMapping()
public class LoginController {
    @Resource
    private ILoginService loginService;

    @Resource
    private IUserService userService;

    @GetMapping("/captcha")
    public ApiResponse<Map<String, Object>> getCaptcha() {
        Map<String, Object> data = loginService.generateCaptcha();
        return ApiResponse.success(data);
    }

    @PostMapping("/login")
    public ApiResponse<Map<String, Object>> login(@RequestBody LoginReq loginReq) {
        Map<String, Object> data = loginService.login(loginReq);
        return ApiResponse.success(data);
    }

    @GetMapping("/auth/user")
    public ApiResponse<User> getUser(HttpServletRequest request) {
        // 获取 Authorization 头部的值
        String authorizationHeader = request.getHeader("Authorization");
        // 解析 Bearer Token
        String token = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7); // 从 "Bearer " 后面开始截取
            String username = JwtUtil.getUsername(token);
            User user = userService.getUserByUsername(username);
            return ApiResponse.success(user);
        }
        return ApiResponse.fail();
    }
}
