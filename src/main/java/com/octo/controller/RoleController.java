package com.octo.controller;


import com.octo.entity.Menu;
import com.octo.entity.Role;
import com.octo.service.IRoleService;
import com.octo.util.ApiResponse;
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
    public ApiResponse editRole(@RequestBody Role role) {
        boolean result = roleService.editRole(role);
        return result ? ApiResponse.success() : ApiResponse.fail();
    }

    @GetMapping("/page")
    public ApiResponse getRoles(String roleName,
                                String roleCode,
                                String comments,
                                String sort,
                                String order,
                                @RequestParam(defaultValue = "1") int page,
                                @RequestParam(defaultValue = "10") int limit) {
        Map<String, Object> roleList = roleService.rolePage(roleName, roleCode, comments, sort, order, page, limit);
        return ApiResponse.success(roleList);
    }

    @DeleteMapping("/{roleNo}")
    public ApiResponse deleteRole(@PathVariable String roleNo) {
        boolean result = roleService.deleteByRoleNo(roleNo);
        return result ? ApiResponse.success() : ApiResponse.fail();
    }

    @DeleteMapping("/batch")
    public ApiResponse deleteRoles(@RequestBody List<String> roleNos) {
        boolean result = roleService.deleteRoleInBatch(roleNos);
        return result ? ApiResponse.success() : ApiResponse.fail();
    }

    @GetMapping("/menu/{roleNo}")
    public ApiResponse getRoleMenus(@PathVariable String roleNo) {
        List<Menu> menuList = roleService.getRoleMenus(roleNo);
        return ApiResponse.success(menuList);
    }

    @PostMapping("/menu/{roleNo}")
    public ApiResponse assignMenu(@PathVariable String roleNo, @RequestBody List<String> menuNos) {
        boolean result = roleService.assignMenu(roleNo, menuNos);
        return result ? ApiResponse.success() : ApiResponse.fail();
    }
}
