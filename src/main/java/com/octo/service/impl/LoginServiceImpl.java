package com.octo.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.core.util.IdUtil;
import com.octo.constants.RedisConstants;
import com.octo.dto.request.LoginReq;
import com.octo.entity.User;
import com.octo.enums.ResponseCodeEnums;
import com.octo.exception.CustomException;
import com.octo.service.ILoginService;
import com.octo.service.IUserService;
import com.octo.util.JwtUtil;
import com.octo.util.RedisUtil;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements ILoginService {

    @Autowired
    private IUserService userService;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Map<String, Object> generateCaptcha() {
        String uuid = IdUtil.simpleUUID();
        int width = 100, height = 40, codeCount = 4;
        CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(width, height, codeCount, 4);
        HashMap<String, Object> data = new HashMap<>();
        data.put("base64", captcha.getImageBase64Data());
        data.put("uuid", uuid);
        data.put("text", captcha.getCode());
        String key = RedisConstants.CAPTCHA_CODE_KEY + uuid;
        redisUtil.set(key, captcha.getCode(), 10, TimeUnit.MINUTES);
        return data;
    }

    @Override
    public Map<String, Object> login(LoginReq loginReq) {
        HashMap<String, Object> map = new HashMap<>();
        String key = RedisConstants.CAPTCHA_CODE_KEY + loginReq.getUuid();
        // 校验验证码
        String code = (String) redisUtil.get(key);
        if (code == null || !code.equals(loginReq.getCode())) {
            throw new CustomException(ResponseCodeEnums.CAPTCHA_ERROR);
        }
        // 查询用户
        User user = userService.getUserByUsername(loginReq.getUsername());
        if (user == null) {
            throw new UnknownAccountException("用户不存在！");
        }
        // 生成token
        String token = JwtUtil.generateToken(user.getUsername());
        map.put("access_token", token);
        map.put("user", user);
        return map;
    }
}
