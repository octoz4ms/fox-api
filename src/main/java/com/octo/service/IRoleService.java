package com.octo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.octo.dto.response.PageResult;
import com.octo.entity.Menu;
import com.octo.entity.Role;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author zms
 * @since 2023-11-23
 */
public interface IRoleService extends IService<Role> {

    PageResult<Role> pageRole(Role role, int pageNum, int pageSize);

    void updateRole(Role role);

    void deleteRole(Long id);

    void deleteRoleInBatch(List<Long> roleIds);

    List<Menu> getMenusByRoleId(Long roleId);

    void assignMenu(Long roleId, List<Long> menuIds);
}
