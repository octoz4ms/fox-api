package com.octo.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.core.util.IdUtil;
import com.octo.constants.RedisConstants;
import com.octo.dto.request.LoginReq;
import com.octo.service.ILoginService;
import com.octo.util.ApiResponse;
import com.octo.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping()
public class LoginController {
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ILoginService loginService;

    @GetMapping("/captcha")
    public ApiResponse<Map<String, Object>> getCaptcha() {
        String uuid = IdUtil.simpleUUID();
        int width = 100, height = 40, codeCount = 4;
        CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(width, height, codeCount, 4);
        HashMap<String, Object> data = new HashMap<>();
        data.put("img", captcha.getImageBase64Data());
        data.put("uuid", uuid);
        String key = RedisConstants.CAPTCHA_CODE_KEY + uuid;
        redisUtil.set(key, captcha.getCode(), 2, TimeUnit.MINUTES);
        return ApiResponse.success(data);
    }

    @PostMapping("/login")
    public ApiResponse<Object> login(@RequestBody LoginReq loginReq) {
        Map<String, Object> data = loginService.login(loginReq);
        return ApiResponse.success(data);
    }
}
