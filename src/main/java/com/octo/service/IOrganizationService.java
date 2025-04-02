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

    List<Organization> listOrganizations(String organizationName, Integer organizationType);

    void addOrganization(Organization organization);

    void updateOrganization(Organization organization);

    void deleteOrganization(Long id);
}
