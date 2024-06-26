package com.octo.controller;


import com.octo.entity.Menu;
import com.octo.entity.Role;
import com.octo.service.IRoleService;
import com.octo.util.Response;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author zms
 * @since 2023-11-23
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    private IRoleService roleService;

    @PostMapping()
    public Response editRole(@RequestBody Role role) {
        boolean result = roleService.editRole(role);
        return result ? Response.success() : Response.fail();
    }

    @GetMapping("/page")
    public Response getRoles(String roleName,
                             String roleCode,
                             String comments,
                             String sort,
                             String order,
                             @RequestParam(defaultValue = "1") int page,
                             @RequestParam(defaultValue = "10") int limit) {
        Map<String, Object> roleList = roleService.rolePage(roleName, roleCode, comments, sort, order, page, limit);
        return Response.success(roleList);
    }

    @DeleteMapping("/{roleNo}")
    public Response deleteRole(@PathVariable String roleNo) {
        boolean result = roleService.deleteByRoleNo(roleNo);
        return result ? Response.success() : Response.fail();
    }

    @DeleteMapping("/batch")
    public Response deleteRoles(@RequestBody List<String> roleNos) {
        boolean result = roleService.deleteRoleInBatch(roleNos);
        return result ? Response.success() : Response.fail();
    }

    @GetMapping("/menu/{roleNo}")
    public Response getRoleMenus(@PathVariable String roleNo) {
        List<Menu> menuList = roleService.getRoleMenus(roleNo);
        return Response.success(menuList);
    }

    @PostMapping("/menu/{roleNo}")
    public Response assignMenu(@PathVariable String roleNo, @RequestBody List<String> menuNos) {
        boolean result = roleService.assignMenu(roleNo, menuNos);
        return result ? Response.success() : Response.fail();
    }
}
