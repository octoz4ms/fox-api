package com.octo.controller;


import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.octo.dto.response.MenuResp;
import com.octo.entity.Menu;
import com.octo.service.IMenuService;
import com.octo.util.Response;
import com.octo.util.SnowIdUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public Map<String, Object> getMenuList() {
        HashMap<String, Object> map = new HashMap<>(10);
        List<MenuResp> menuList = menuService.getMenuList();
        map.put("data", menuList);
        map.put("status", "ok");
        map.put("msg", "请求成功");
        map.put("code", 20000);
        return map;
    }

    @GetMapping("/options")
    public Response getMenuOptions(String name){
        List<Menu> menuList = menuService.list(Wrappers.lambdaQuery(Menu.class)
                .select(Menu::getMenuNo, Menu::getLocale)
                .like(StrUtil.isNotEmpty(name), Menu::getLocale, name)
        );
        List<HashMap<String, Object>> data = menuList.stream().map(m -> {
            HashMap<String, Object> map = new HashMap<>(10);
            map.put("menuNo", m.getMenuNo());
            map.put("menuName", m.getLocale());
            return map;
        }).collect(Collectors.toList());
        return Response.success(data);
    }

    @PostMapping("/add")
    public String editMenu(){
        return SnowIdUtil.nextIdStr();
    }

}
