package com.octo.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.octo.dto.response.PageResult;
import com.octo.entity.*;
import com.octo.enums.ResponseCodeEnums;
import com.octo.exception.CustomException;
import com.octo.listener.UserExcelListener;
import com.octo.mapper.UserMapper;
import com.octo.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
            UserServiceImpl.log.error("userRole:{}", userRoles);
            if (!userRoles.isEmpty()) {
                List<Long> roleIds = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());
                UserServiceImpl.log.error("roleIds:{}", roleIds);
                List<Role> roles = roleService.list(Wrappers.lambdaQuery(Role.class).in(Role::getId, roleIds));

                u.setRoles(roles);
            } else {
                u.setRoles(List.of());
            }
        });
        return new PageResult<>(userPage);
    }

    @Override
    @Transactional
    public void createUser(User user) {
        boolean save = save(user);
        if (!save) {
            throw new CustomException(ResponseCodeEnums.FAIL);
        }
        if (user.getRoles().isEmpty()) {
            return;
        }
        saveUserRoles(user.getId(), user.getRoles());
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        if (user.getId() == null) {
            throw new CustomException(500, "用户不存在");
        }
        LambdaUpdateWrapper<User> updateWrapper = Wrappers.lambdaUpdate(User.class)
                .eq(User::getId, user.getId())
                .set(User::getOrganizationId, user.getOrganizationId())
                .set(User::getPhone, user.getPhone())
                .set(User::getBirthday, user.getBirthday())
                .set(User::getNickname, user.getNickname())
                .set(User::getStatus, user.getStatus())
                .set(User::getSex, user.getSex())
                .set(User::getDescription, user.getDescription())
                .set(User::getEmail, user.getEmail());

        boolean update = update(new User(), updateWrapper);
        if (!update) {
            throw new CustomException(ResponseCodeEnums.FAIL);
        }
        // 删除用户角色联系
        userRoleService.remove(Wrappers.lambdaQuery(UserRole.class).eq(UserRole::getUserId, user.getId()));
        // 重新建立角色联系
        saveUserRoles(user.getId(), user.getRoles());
    }


    public void saveUserRoles(Long userId, List<Role> roles) {
        List<UserRole> userRoles = roles.stream().map(role -> {
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(role.getId());
            return userRole;
        }).toList();
        userRoleService.saveBatch(userRoles);
    }
}
