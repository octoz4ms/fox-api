package com.octo.controller;


import com.octo.entity.Menu;
import com.octo.entity.Role;
import com.octo.service.IRoleService;
import com.octo.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 角色菜单表 前端控制器
 * </p>
 *
 * @author zms
 * @since 2025-04-08
 */
@RestController
@RequestMapping("system/role-menu")
public class RoleMenuController {

    @Autowired
    private IRoleService roleService;

    @GetMapping("/{id}")
    public ApiResponse<List<Menu>> getMenusByRoleId(@PathVariable Long id) {
        List<Menu> menus = roleService.getMenusByRoleId(id);
        return ApiResponse.success(menus);
    }

    @PutMapping("/{id}")
    public ApiResponse<Role> updateRoleMenu(@PathVariable Long id, @RequestBody List<Long> menuIds) {
        roleService.assignMenu(id, menuIds);
        return ApiResponse.success();
    }
}
