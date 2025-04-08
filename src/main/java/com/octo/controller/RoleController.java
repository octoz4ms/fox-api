package com.octo.controller;


import com.octo.dto.response.PageResult;
import com.octo.entity.Role;
import com.octo.service.IRoleService;
import com.octo.util.ApiResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import static com.baomidou.mybatisplus.extension.toolkit.Db.save;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author zms
 * @since 2023-11-23
 */
@RestController
@RequestMapping("/system/role")
public class RoleController {

    @Resource
    private IRoleService roleService;

    @GetMapping("/page")
    public ApiResponse<PageResult<Role>> pageRoles(Role role,
                                                   @RequestParam(name = "page", defaultValue = "1") int pageNum,
                                                   @RequestParam(name = "limit", defaultValue = "10") int pageSize) {
        PageResult<Role> roles = roleService.pageRole(role, pageNum, pageSize);
        return ApiResponse.success(roles);
    }

    @GetMapping
    public ApiResponse<List<Role>> listRoles() {
        List<Role> list = roleService.list();
        return ApiResponse.success(list);
    }

    @PostMapping
    public ApiResponse<Role> createRole(@RequestBody Role role) {
        boolean save = save(role);
        return save ? ApiResponse.success() : ApiResponse.fail();
    }

    @PutMapping
    public ApiResponse<Role> updateRole(@RequestBody Role role) {
        roleService.updateRole(role);
        return ApiResponse.success();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Role> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ApiResponse.success();
    }

    @DeleteMapping("/batch")
    public ApiResponse<Role> deleteRoles(@RequestBody List<Long> roleIds) {
        roleService.deleteRoleInBatch(roleIds);
        return ApiResponse.success();
    }
}
