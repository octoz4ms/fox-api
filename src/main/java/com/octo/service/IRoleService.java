package com.octo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.octo.entity.Menu;
import com.octo.entity.Role;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author zms
 * @since 2023-11-23
 */
public interface IRoleService extends IService<Role> {

    /**
     * 保存、更新角色
     *
     * @param role
     * @return
     */
    boolean editRole(Role role);

    /**
     * 分页查询角色列表
     *
     * @param roleName
     * @param roleCode
     * @param comments
     * @param sort
     * @param order
     * @param page
     * @param limit
     * @return
     */
    Map<String, Object> rolePage(String roleName, String roleCode, String comments, String sort, String order, int page, int limit);

    /**
     * 删除角色
     *
     * @param roleNo
     * @return
     */
    boolean deleteByRoleNo(String roleNo);

    /**
     * 批量删除角色
     *
     * @param roleNos
     * @return
     */
    boolean deleteRoleInBatch(List<String> roleNos);

    /**
     * 获取角色分配的菜单
     *
     * @return
     */
    List<Menu> getRoleMenus(String roleNo);

    /**
     * 分配菜单
     *
     * @param roleNo
     * @param menuNos
     * @return
     */
    boolean assignMenu(String roleNo, List<String> menuNos);
}
