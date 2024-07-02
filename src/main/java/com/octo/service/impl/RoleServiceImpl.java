package com.octo.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.octo.entity.Menu;
import com.octo.entity.Role;
import com.octo.entity.RoleMenu;
import com.octo.mapper.RoleMapper;
import com.octo.service.IMenuService;
import com.octo.service.IRoleMenuService;
import com.octo.service.IRoleService;
import com.octo.util.GeneralUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author zms
 * @since 2023-11-23
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
    @Resource
    private IRoleMenuService roleMenuService;
    @Resource
    private IMenuService menuService;

    @Override
    public boolean editRole(Role role) {
        if (StrUtil.isBlank(role.getRoleNo())) {
            role.setRoleNo(GeneralUtil.nextIdStr());
            role.setCreateTime(LocalDateTime.now());
            return save(role);
        }
        return update(role, new LambdaQueryWrapper<Role>().eq(Role::getRoleNo, role.getRoleNo()));
    }

    @Override
    public Map<String, Object> rolePage(String roleName, String roleCode, String comments, String sort, String order, int page, int limit) {
        // 分页
        Page<Role> rolePage = new Page<>(page, limit);
        // 排序
        rolePage.addOrder(new OrderItem(StrUtil.toUnderlineCase(sort), "asc".equalsIgnoreCase(order)));
        // 筛选
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(roleName), Role::getRoleName, roleName)
                .like(StrUtil.isNotBlank(roleCode), Role::getRoleCode, roleCode)
                .like(StrUtil.isNotBlank(comments), Role::getComments, comments);

        page(rolePage, queryWrapper);
        return GeneralUtil.resultPage(rolePage);
    }

    @Override
    public boolean deleteByRoleNo(String roleNo) {
        if (StrUtil.isBlank(roleNo)) {
            return false;
        }
        roleMenuService.remove(Wrappers.lambdaQuery(RoleMenu.class).eq(RoleMenu::getRoleNo, roleNo));
        return remove(Wrappers.lambdaQuery(Role.class).eq(Role::getRoleNo, roleNo));
    }

    @Override
    public boolean deleteRoleInBatch(List<String> roleNos) {
        if (ObjectUtil.isEmpty(roleNos) || roleNos.size() == 0) {
            return false;
        }
        roleMenuService.remove(Wrappers.lambdaQuery(RoleMenu.class).in(RoleMenu::getRoleNo, roleNos));
        return remove(Wrappers.lambdaQuery(Role.class).in(Role::getRoleNo, roleNos));
    }

    @Override
    public List<Menu> getRoleMenus(String roleNo) {
        // 所有菜单
        List<Menu> menuList = menuService.list();
        // 该角色拥有的菜单对应No集合
        List<String> menuNos = roleMenuService.list(Wrappers.lambdaQuery(RoleMenu.class).select(RoleMenu::getMenuNo).eq(RoleMenu::getRoleNo, roleNo)).stream().map(roleMenu -> roleMenu.getMenuNo()).collect(Collectors.toList());
        menuList = menuList.stream().map(menu -> {
            if (menuNos.contains(menu.getMenuNo())) {
                menu.setChecked(true);
            }
            return menu;
        }).collect(Collectors.toList());
        return menuList;
    }

    @Override
    public boolean assignMenu(String roleNo, List<String> menuNos) {
        roleMenuService.remove(Wrappers.lambdaQuery(RoleMenu.class).eq(RoleMenu::getRoleNo, roleNo));
        List<RoleMenu> roleMenuList = menuNos.stream().map(menuNo -> {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setMenuNo(menuNo);
            roleMenu.setRoleNo(roleNo);
            return roleMenu;
        }).collect(Collectors.toList());
        return roleMenuService.saveBatch(roleMenuList);
    }
}
