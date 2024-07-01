package com.octo.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.octo.constant.RedisConstants;
import com.octo.dto.request.LoginReq;
import com.octo.entity.Account;
import com.octo.service.IAccountService;
import com.octo.service.ILoginService;
import com.octo.util.JwtUtil;
import com.octo.util.RedisUtil;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginServiceImpl implements ILoginService {

    @Autowired
    private IAccountService accountService;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Map<String, Object> login(LoginReq loginReq) {
        HashMap<String, Object> map = new HashMap<>();
        String key = RedisConstants.CAPTCHA_CODE_KEY + loginReq.getUuid();
        // 校验验证码
        String code = (String) redisUtil.get(key);
        if (code == null || !code.equals(loginReq.getCode())) {
            throw new RuntimeException("验证码错误");
        }
        // 查询用户
        Account account = accountService.getOne(Wrappers.lambdaQuery(Account.class).eq(Account::getAccountName, loginReq.getUsername()));
        if (account == null) {
            throw new UnknownAccountException("用户不存在！");
        }
        // 生成token
        String token = JwtUtil.generateToken(account.getAccountName());
        map.put("access_token", token);
        map.put("user", account);
        return map;
    }
}
