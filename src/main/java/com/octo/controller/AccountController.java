package com.octo.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.octo.dto.request.LoginReq;
import com.octo.entity.Account;
import com.octo.service.IAccountService;
import com.octo.util.ApiResponse;
import com.octo.util.JwtUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zms
 * @since 2023-11-23
 */
@RestController
@RequestMapping("/account")
public class AccountController {

    @Resource
    private IAccountService accountService;

    @PostMapping("/login")
    public ApiResponse<?> login(@RequestBody LoginReq param) {
        HashMap<String, Object> data = new HashMap<>(2);
        Account account = accountService.getOne(new LambdaQueryWrapper<Account>().eq(Account::getAccountName, param.getUsername()).eq(Account::getPassword, param.getPassword()));
        if (account != null) {
            String token = JwtUtil.generateToken(account.getAccountName());
            data.put("token", token);
            return ApiResponse.success(data);
        }
        return ApiResponse.fail("账号密码错误");
    }

    @GetMapping("/page")
    public ApiResponse<?> getAccounts(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int limit) {
        Map<String, Object> accountList = accountService.getAccountPage(page, limit);
        return ApiResponse.success(accountList);
    }

    @GetMapping("/info")
    public ApiResponse<?> getInfo(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String accountName = JwtUtil.getUsername(token);
        return accountService.getAccount(accountName);
    }

}