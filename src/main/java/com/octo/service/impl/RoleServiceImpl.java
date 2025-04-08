package com.octo.service.impl;

import com.alibaba.excel.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.octo.dto.response.PageResult;
import com.octo.entity.Menu;
import com.octo.entity.Role;
import com.octo.entity.RoleMenu;
import com.octo.enums.ResponseCodeEnums;
import com.octo.exception.CustomException;
import com.octo.mapper.RoleMapper;
import com.octo.service.IMenuService;
import com.octo.service.IRoleMenuService;
import com.octo.service.IRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author zms
 * @since 2023-11-23
 */
@Slf4j
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
    @Resource
    private IRoleMenuService roleMenuService;
    @Resource
    private IMenuService menuService;

    @Override
    public PageResult<Role> pageRole(Role role, int pageNum, int pageSize) {
        // 分页
        Page<Role> page = new Page<>(pageNum, pageSize);

        // 动态条件构建
        LambdaQueryWrapper<Role> queryWrapper = Wrappers.lambdaQuery(Role.class)
                .like(StringUtils.isNotBlank(role.getRoleName()), Role::getRoleName, role.getRoleName())
                .like(StringUtils.isNotBlank(role.getRoleCode()), Role::getRoleCode, role.getRoleCode());

        Page<Role> rolePage = page(page, queryWrapper);
        return new PageResult<>(rolePage);
    }

    @Override
    public void updateRole(Role role) {
        if (role.getId() == null) {
            throw new CustomException(500, "该角色不存在");
        }
        LambdaUpdateWrapper<Role> updateWrapper = Wrappers.lambdaUpdate(Role.class)
                .eq(Role::getId, role.getId())
                .set(Role::getRoleName, role.getRoleName())
                .set(Role::getRoleCode, role.getRoleCode())
                .set(Role::getComments, role.getComments());

        boolean update = update(new Role(), updateWrapper);
        if (!update) {
            throw new CustomException(ResponseCodeEnums.FAIL);
        }
    }

    @Override
    public void deleteRole(Long id) {
        boolean remove = removeById(id);
        if (!remove) {
            throw new CustomException(ResponseCodeEnums.FAIL);
        }
        roleMenuService.remove(new LambdaQueryWrapper<RoleMenu>().eq(RoleMenu::getMenuId, id));
    }

    @Override
    @Transactional
    public void deleteRoleInBatch(List<Long> roleIds) {
        if (roleIds.isEmpty()) {
            return;
        }
        boolean remove = removeByIds(roleIds);
        if (!remove) {
            throw new CustomException(ResponseCodeEnums.FAIL);
        }
        roleMenuService.remove(new LambdaQueryWrapper<RoleMenu>().in(RoleMenu::getRoleId, roleIds));
    }

    @Override
    public List<Menu> getMenusByRoleId(Long roleId) {
        List<RoleMenu> list = roleMenuService.list(Wrappers.<RoleMenu>lambdaQuery().eq(RoleMenu::getRoleId, roleId));
        List<Long> menuIds = list.stream().map(RoleMenu::getMenuId).toList();
        return menuService.list().stream().peek(menu -> {
            boolean checked = menuIds.contains(menu.getId());
            menu.setChecked(checked);
        }).toList();
    }

    @Override
    @Transactional
    public void assignMenu(Long roleId, List<Long> menuIds) {
        // 删除原有权限
        roleMenuService.removeById(roleId);
        // 批量插入新权限
        if (menuIds.isEmpty()) {
            return;
        }
        List<RoleMenu> roleMenus = menuIds.stream().map(id -> new RoleMenu().setRoleId(roleId).setMenuId(id)).toList();
        RoleServiceImpl.log.info("roleMenus:{}", roleMenus);
        roleMenuService.saveBatch(roleMenus);
    }
}
