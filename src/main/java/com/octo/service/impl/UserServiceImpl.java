package com.octo.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.octo.entity.*;
import com.octo.listener.UserExcelListener;
import com.octo.mapper.UserMapper;
import com.octo.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
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
        User user = getOne(Wrappers.lambdaQuery(User.class).eq(User::getUsername, username));
        if (user == null) {
            return null;
        }
        // 获取角色
        List<String> roleNos = userRoleService.list(Wrappers.lambdaQuery(UserRole.class)
                        .select(UserRole::getRoleNo)
                        .eq(UserRole::getUserNo, user.getUserNo()))
                .stream().map(r -> r.getRoleNo()).collect(Collectors.toList());
        if (roleNos.isEmpty()) {
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
        if (menuNos.isEmpty()) {
            user.setAuthorities(List.of());
            return user;
        }
        List<Menu> menuList = menuService.list(Wrappers.lambdaQuery(Menu.class).in(Menu::getMenuNo, menuNos).orderByAsc(Menu::getSortNumber));
        user.setAuthorities(menuList);
        return user;
    }

    @Override
    public void importExcel(MultipartFile file) {
        if (file.isEmpty()) {
            throw new RuntimeException("文件不能为空");
        }

        try {
            UserExcelListener listener = new UserExcelListener();
            EasyExcel.read(file.getInputStream(), ExcelUser.class, listener).sheet().doRead();
            ArrayList<User> users = new ArrayList<>();
            for (ExcelUser excelUser : listener.getDataList()) {
                User user = new User();
                BeanUtils.copyProperties(excelUser, user);
                users.add(user);
            }
        } catch (IOException e) {
            throw new RuntimeException("导入失败");
        }
    }
}
