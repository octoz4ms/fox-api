package com.octo.controller;


import com.octo.entity.Organization;
import com.octo.service.IOrganizationService;
import com.octo.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 机构表 前端控制器
 * </p>
 *
 * @author zms
 * @since 2025-04-02
 */
@RestController
@RequestMapping("/organization")
public class OrganizationController {

    @Autowired
    private IOrganizationService organizationService;

    @GetMapping
    public ApiResponse<?> listOrganizations(@RequestParam(name = "organizationName") String organizationName,
                                            @RequestParam(name = "organizationType") Integer organizationType) {
        List<Organization> organizations = organizationService.listOrganizations(organizationName, organizationType);
        return ApiResponse.success(organizations);
    }

    @PostMapping
    public ApiResponse<?> addOrganization(@RequestBody Organization organization) {
        organizationService.addOrganization(organization);
        return ApiResponse.success();
    }

    @PutMapping
    public ApiResponse<?> updateOrganization(@RequestBody Organization organization) {
        organizationService.updateOrganization(organization);
        return ApiResponse.success();
    }

    @DeleteMapping("{id}")
    public ApiResponse<?> deleteOrganization(@PathVariable Long id) {
        organizationService.deleteOrganization(id);
        return ApiResponse.success();
    }
}
