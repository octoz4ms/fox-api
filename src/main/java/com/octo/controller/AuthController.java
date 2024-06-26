package com.octo.controller;

import com.octo.service.IAccountService;
import com.octo.util.JwtUtil;
import com.octo.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private IAccountService accountService;

    @GetMapping("/user")
    public Response getUser(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String accountName = JwtUtil.getAccountName(token);
        return accountService.getAccount(accountName);
    }
}
