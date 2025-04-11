package com.octo.controller;


import com.octo.dto.response.PageResult;
import com.octo.entity.User;
import com.octo.service.IUserService;
import com.octo.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author zms
 * @since 2024-07-02
 */
@RestController
@RequestMapping("/system/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/page")
    public ApiResponse<PageResult<User>> pageUsers(User user,
                                                   @RequestParam(name = "sort", required = false) String sortField,
                                                   @RequestParam(name = "order", required = false) String sortOrder,
                                                   @RequestParam(name = "page", defaultValue = "1") Integer pageNum,
                                                   @RequestParam(name = "limit", defaultValue = "10") Integer pageSize) {
        PageResult<User> users = userService.pageUsers(user, sortField, sortOrder, pageNum, pageSize);
        return ApiResponse.success(users);
    }

    @PostMapping
    public ApiResponse<User> createUser(@RequestBody User user) {
        userService.createUser(user);
        return ApiResponse.success();
    }

    @PutMapping
    public ApiResponse<User> updateUser(@RequestBody User user) {
        userService.updateUser(user);
        return ApiResponse.success();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<User> deleteUser(@PathVariable Long id) {
        userService.removeById(id);
        return ApiResponse.success();
    }

    @DeleteMapping("/batch")
    public ApiResponse<User> deleteUsers(@RequestBody List<Long> ids) {
        userService.deleteUsersByIds(ids);
        return ApiResponse.success();
    }

    @GetMapping("/existence")
    public ApiResponse<List<User>> existenceUser(String field, String value) {
        boolean existing = userService.existenceUser(field, value);
        return existing ? ApiResponse.success() : ApiResponse.fail();
    }

    @PutMapping("/password")
    public ApiResponse<User> resetPassword(@RequestBody User user) {
        userService.resetPassword(user);
        return ApiResponse.success();
    }

    @PutMapping("/status")
    public ApiResponse<User> updateStatus(@RequestBody User user) {
        userService.updateStatus(user.getId(), user.getStatus());
        return ApiResponse.success();
    }
}
