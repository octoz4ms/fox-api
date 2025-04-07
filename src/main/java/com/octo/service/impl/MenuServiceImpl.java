package com.octo.service.impl;

import com.alibaba.excel.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.octo.entity.Menu;
import com.octo.entity.RoleMenu;
import com.octo.enums.ResponseCodeEnums;
import com.octo.exception.CustomException;
import com.octo.mapper.MenuMapper;
import com.octo.service.IMenuService;
import com.octo.service.IRoleMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Slf4j
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Resource
    private IRoleMenuService roleMenuService;

    @Override
    public List<Menu> getMenuList(String title, String path, String authority) {
        LambdaQueryWrapper<Menu> queryWrapper = Wrappers.lambdaQuery(Menu.class)
                .eq(StringUtils.isNotBlank(title), Menu::getTitle, title)
                .eq(StringUtils.isNotBlank(path), Menu::getPath, path)
                .eq(StringUtils.isNotBlank(authority), Menu::getAuthority, authority);
        return list(queryWrapper);
    }

    @Override
    public void updateMenu(Menu menu) {
        if (menu.getId() == null) {
            throw new CustomException(500, "菜单不存在");
        }
        if (menu.getId().equals(menu.getParentId())) {
            throw new CustomException(500, "父级菜单不能是自己");
        }
        LambdaUpdateWrapper<Menu> updateWrapper = Wrappers.lambdaUpdate(Menu.class)
                .eq(Menu::getId, menu.getId())
                .set(Menu::getParentId, menu.getParentId())
                .set(Menu::getMenuType, menu.getMenuType())
                .set(Menu::getTitle, menu.getTitle())
                .set(Menu::getIcon, menu.getIcon())
                .set(Menu::getAuthority, menu.getAuthority())
                .set(Menu::getPath, menu.getPath())
                .set(Menu::getSortNumber, menu.getSortNumber())
                .set(Menu::getComponent, menu.getComponent())
                .set(Menu::getHide, menu.getHide())
                .set(Menu::getMeta, menu.getMeta());
        boolean update = update(new Menu(), updateWrapper);
        if (!update) {
            throw new CustomException(ResponseCodeEnums.FAIL);
        }
    }

    @Override
    @Transactional
    public void deleteMenu(Long id) {
        boolean remove = removeById(id);
        if (!remove) {
            throw new CustomException(ResponseCodeEnums.FAIL);
        }
        roleMenuService.remove(new LambdaQueryWrapper<RoleMenu>().eq(RoleMenu::getMenuId, id));
    }
}
