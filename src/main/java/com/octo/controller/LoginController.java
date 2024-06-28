package com.octo.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.core.util.IdUtil;
import com.octo.util.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;


@RestController
@RequestMapping()
public class LoginController {

    @GetMapping("/captcha")
    public ApiResponse getCaptcha() {
        String uuid = IdUtil.simpleUUID();
        int width = 100, height = 40, codeCount = 15;
        CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(width, height, codeCount, 4);
        HashMap<String, Object> data = new HashMap<>();
        data.put("img", captcha.getImageBase64Data());
        data.put("uuid", uuid);
        return ApiResponse.success(data);
    }
}
