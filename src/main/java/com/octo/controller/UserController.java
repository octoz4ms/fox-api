package com.octo.controller;


import com.octo.dto.response.PageResult;
import com.octo.entity.User;
import com.octo.service.IUserService;
import com.octo.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/page")
    public ApiResponse<PageResult<User>> pageUsers(User user,
                                                   @RequestParam(name = "page", defaultValue = "1") Integer pageNum,
                                                   @RequestParam(name = "limit", defaultValue = "10") Integer pageSize) {
        PageResult<User> users = userService.pageUsers(user, pageNum, pageSize);
        return ApiResponse.success(users);
    }
}
