package com.octo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.octo.entity.Organization;

import java.util.List;

/**
 * <p>
 * 机构表 服务类
 * </p>
 *
 * @author zms
 * @since 2025-04-02
 */
public interface IOrganizationService extends IService<Organization> {

    List<Organization> listOrganizations(Organization organization, String sortField, String sortOrder);

    void updateOrganization(Organization organization);

    List<Long> getOrgAndSubOrgIds(Long organizationId);

}
