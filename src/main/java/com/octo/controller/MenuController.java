package com.octo.controller;


import com.octo.entity.Menu;
import com.octo.service.IMenuService;
import com.octo.util.ApiResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author zms
 * @since 2023-11-23
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Resource
    private IMenuService menuService;

    @GetMapping
    public ApiResponse<List<Menu>> menuList(String title, String path, String authority) {
        List<Menu> menuList = menuService.getMenuList(title, path, authority);
        return ApiResponse.success(menuList);
    }

    @PostMapping
    public ApiResponse<Menu> createMenu(@RequestBody Menu menu) {
        boolean saved = menuService.save(menu);
        return saved ? ApiResponse.success() : ApiResponse.fail();
    }

    @PutMapping
    public ApiResponse<Menu> updateMenu(@RequestBody Menu menu) {
        menuService.updateMenu(menu);
        return ApiResponse.success();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Menu> deleteMenu(@PathVariable Long id) {
        menuService.deleteMenu(id);
        return ApiResponse.success();
    }
}
