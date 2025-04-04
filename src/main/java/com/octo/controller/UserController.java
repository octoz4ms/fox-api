package com.octo.controller;


import com.octo.entity.User;
import com.octo.service.IUserService;
import com.octo.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    private IUserService userService;

    @GetMapping("/{id}")
    public ApiResponse<User> getUser(@PathVariable("id") int id) {
        return ApiResponse.success();
    }

    @PostMapping("/import/excel")
    public ApiResponse<?> importExcel(@RequestParam("file") MultipartFile file) {
        userService.importExcel(file);
        return ApiResponse.success();
    }
}
