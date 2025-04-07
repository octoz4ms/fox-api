package com.octo.service.impl;

import com.alibaba.excel.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.octo.entity.Organization;
import com.octo.enums.ResponseCodeEnums;
import com.octo.exception.CustomException;
import com.octo.mapper.OrganizationMapper;
import com.octo.service.IOrganizationService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 机构表 服务实现类
 * </p>
 *
 * @author zms
 * @since 2025-04-02
 */
@Service
public class OrganizationServiceImpl extends ServiceImpl<OrganizationMapper, Organization> implements IOrganizationService {

    @Override
    public List<Organization> listOrganizations(String organizationName, Integer organizationType) {
        LambdaQueryWrapper<Organization> queryWrapper = Wrappers.lambdaQuery(Organization.class)
                .eq(StringUtils.isNotBlank(organizationName), Organization::getOrganizationName, organizationName)
                .eq(organizationType != null, Organization::getOrganizationType, organizationType);
        return list(queryWrapper);
    }

    @Override
    public void updateOrganization(Organization organization) {
        if (organization.getId() == null) {
            throw new CustomException(500, "该机构不存在");
        }
        LambdaUpdateWrapper<Organization> updateWrapper = Wrappers.lambdaUpdate(Organization.class)
                .eq(Organization::getId, organization.getId())
                .set(Organization::getParentId, organization.getParentId())
                .set(Organization::getOrganizationType, organization.getOrganizationType())
                .set(Organization::getOrganizationName, organization.getOrganizationName())
                .set(Organization::getSortNumber, organization.getSortNumber())
                .set(Organization::getOrganizationFullName, organization.getOrganizationFullName())
                .set(Organization::getComments, organization.getComments())
                .set(Organization::getOrganizationCode, organization.getOrganizationCode());
        boolean update = update(new Organization(), updateWrapper);
        if (!update) {
            throw new CustomException(ResponseCodeEnums.FAIL);
        }
    }
}
