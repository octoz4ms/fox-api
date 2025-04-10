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
@RequestMapping("/system/organization")
public class OrganizationController {

    @Autowired
    private IOrganizationService organizationService;

    @GetMapping
    public ApiResponse<?> listOrganizations(Organization organization,
                                            @RequestParam(name = "sort", required = false) String sortField,
                                            @RequestParam(name = "order", required = false) String sortOrder) {
        List<Organization> organizations = organizationService.listOrganizations(organization, sortField, sortOrder);
        return ApiResponse.success(organizations);
    }

    @PostMapping
    public ApiResponse<?> createOrganization(@RequestBody Organization organization) {
        boolean save = organizationService.save(organization);
        return save ? ApiResponse.success() : ApiResponse.fail();
    }

    @PutMapping
    public ApiResponse<?> updateOrganization(@RequestBody Organization organization) {
        organizationService.updateOrganization(organization);
        return ApiResponse.success();
    }

    @DeleteMapping("{id}")
    public ApiResponse<?> deleteOrganization(@PathVariable Long id) {
        boolean remove = organizationService.removeById(id);
        return remove ? ApiResponse.success() : ApiResponse.fail();
    }
}
