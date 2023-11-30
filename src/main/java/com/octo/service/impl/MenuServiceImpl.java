package com.octo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.octo.dto.response.MenuResp;
import com.octo.entity.Menu;
import com.octo.mapper.MenuMapper;
import com.octo.service.IMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author zms
 * @since 2023-11-23
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Resource
    private MenuMapper menuMapper;

    @Override
    public List<MenuResp> getMenuList() {
        System.out.println(list());
        return MenuResp.buildMenuTree(list());
    }
}
