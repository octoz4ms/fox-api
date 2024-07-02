package com.octo.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.octo.entity.*;
import com.octo.mapper.UserMapper;
import com.octo.service.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author zms
 * @since 2024-07-02
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private IMenuService menuService;
    @Resource
    private IRoleMenuService roleMenuService;
    @Resource
    private IUserRoleService userRoleService;
    @Resource
    private IRoleService roleService;

    @Override
    public User getUserByUsername(String username) {
        User user = getOne(Wrappers.lambdaQuery(User.class).eq(User::getUserName, username));
        if (user == null) {
            return null;
        }
        // 获取角色
        List<String> roleNos = userRoleService.list(Wrappers.lambdaQuery(UserRole.class)
                        .select(UserRole::getRoleNo)
                        .eq(UserRole::getUserNo, user.getUserNo()))
                .stream().map(r -> r.getRoleNo()).collect(Collectors.toList());
        if (roleNos.size() == 0) {
            user.setRoles(List.of());
            user.setAuthorities(List.of());
            return user;
        }
        List<Role> roleList = roleService.list(Wrappers.lambdaQuery(Role.class).in(Role::getRoleNo, roleNos));
        user.setRoles(roleList);
        // 获取菜单
        List<String> menuNos = roleMenuService.list(Wrappers.lambdaQuery(RoleMenu.class)
                        .select(RoleMenu::getMenuNo)
                        .in(RoleMenu::getRoleNo, roleNos))
                .stream().map(m -> m.getMenuNo()).collect(Collectors.toList());
        if (menuNos.size() == 0) {
            user.setAuthorities(List.of());
            return user;
        }
        List<Menu> menuList = menuService.list(Wrappers.lambdaQuery(Menu.class).in(Menu::getMenuNo, menuNos));
        user.setAuthorities(menuList);
        return user;
    }
}
