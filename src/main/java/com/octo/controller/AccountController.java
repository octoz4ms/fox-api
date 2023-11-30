package com.octo.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.octo.dto.request.UserParam;
import com.octo.entity.Account;
import com.octo.service.IAccountService;
import com.octo.util.JwtUtil;
import com.octo.util.Response;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
    public Response login(@RequestBody UserParam param) {
        System.out.println(param);
        HashMap<String, Object> data = new HashMap<>(2);
        Account account = accountService.getOne(new LambdaQueryWrapper<Account>().eq(Account::getAccountName, param.getUsername()).eq(Account::getPassword, param.getPassword()));
        if (account != null) {
            String token = JwtUtil.generateToken(account.getAccountName());
            data.put("token", token);
            return Response.success(data);
        }
        return Response.fail("账号密码错误");
    }

    @GetMapping("list")
    public Response getUserList(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "20") int size) {
        Page<Account> accountPage = new Page<>(page, size);
        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        return Response.success(accountService.page(accountPage, queryWrapper));
    }

    @PostMapping("/logout")
    public String logout(@RequestBody Map<String, String> map) {
        System.out.println("username: " + map.get("username"));
        System.out.println("password: " + map.get("password"));
        return "退出！";
    }

}
