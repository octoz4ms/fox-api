package com.octo.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.octo.dto.response.PageResult;
import com.octo.entity.*;
import com.octo.listener.UserExcelListener;
import com.octo.mapper.UserMapper;
import com.octo.service.*;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
        List<Long> roleIds = userRoleService.list(Wrappers.lambdaQuery(UserRole.class)
                        .select(UserRole::getRoleId)
                        .eq(UserRole::getUserId, user.getId()))
                .stream().map(UserRole::getRoleId).collect(Collectors.toList());
        if (roleIds.isEmpty()) {
            user.setRoles(List.of());
            user.setAuthorities(List.of());
            return user;
        }
        List<Role> roleList = roleService.list(Wrappers.lambdaQuery(Role.class).in(Role::getId, roleIds));
        user.setRoles(roleList);
        // 获取菜单
        List<Long> menuIds = roleMenuService.list(Wrappers.lambdaQuery(RoleMenu.class)
                        .select(RoleMenu::getMenuId)
                        .in(RoleMenu::getRoleId, roleIds))
                .stream().map(RoleMenu::getMenuId).collect(Collectors.toList());
        if (menuIds.isEmpty()) {
            user.setAuthorities(List.of());
            return user;
        }
        List<Menu> menuList = menuService.list(Wrappers.lambdaQuery(Menu.class).in(Menu::getId, menuIds).orderByAsc(Menu::getSortNumber));
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

    @Override
    public PageResult<User> pageUsers(User user, Integer pageNum, Integer pageSize) {
        // 分页参数
        Page<User> page = new Page<>(pageNum, pageSize);

        // 动态条件构建
        LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery(User.class)
                .like(StringUtils.isNotBlank(user.getUsername()), User::getUsername, user.getUsername())
                .like(StringUtils.isNotBlank(user.getNickname()), User::getNickname, user.getNickname())
                .eq(user.getSex() != null, User::getSex, user.getSex())
                .eq(user.getOrganizationId() != null, User::getOrganizationId, user.getOrganizationId());
        Page<User> userPage = page(page, queryWrapper);

        // 查询角色
        userPage.getRecords().forEach(u -> {
            List<UserRole> userRoles = userRoleService.list(Wrappers.lambdaQuery(UserRole.class)
                    .select(UserRole::getRoleId)
                    .eq(UserRole::getUserId, u.getId()));
            log.error("userRole:{}", userRoles);
            if(!userRoles.isEmpty()){
                List<Long> roleIds = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());
                log.error("roleIds:{}",roleIds);
                List<Role> roles = roleService.list(Wrappers.lambdaQuery(Role.class).in(Role::getId, roleIds));

                u.setRoles(roles);
            }else{
                u.setRoles(List.of());
            }
        });
        return new PageResult<>(userPage);
    }
}
