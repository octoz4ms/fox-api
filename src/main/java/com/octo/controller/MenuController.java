package com.octo.controller;


import com.octo.entity.Menu;
import com.octo.service.IMenuService;
import com.octo.util.Response;
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
    public Response<List> menuList(String title, String path, String authority) {
        return Response.success(menuService.getMenuList(title, path, authority));
    }

    @PostMapping
    public Response editMenu(@RequestBody Menu menu) {
        boolean result = menuService.saveMenu(menu);
        return result ? Response.success() : Response.fail();
    }

    @DeleteMapping("/{menuNo}")
    public Response deleteMenu(@PathVariable String menuNo) {
        return menuService.deleteMenu(menuNo);
    }
}
