package com.octo.controller;


import cn.hutool.core.lang.tree.Tree;
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

    @PostMapping("/list")
    public Response getMenuList() {
        List<Tree<String>> menuList = menuService.getMenuList();
        return Response.success(menuList);
    }

    @GetMapping("/tree/options")
    public Response getMenuTreeOptions(String name) {
        return Response.success(menuService.getMenuTreeOptions(name));
    }

    @PostMapping("/edit")
    public Response editMenu(@RequestBody Menu menu) {
        return Response.success(menuService.saveMenu(menu));
    }

}
