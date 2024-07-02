package com.octo.controller;

import com.octo.dto.request.LoginReq;
import com.octo.service.ILoginService;
import com.octo.util.ApiResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;


@RestController
@RequestMapping()
public class LoginController {
    @Resource
    private ILoginService loginService;

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
}
