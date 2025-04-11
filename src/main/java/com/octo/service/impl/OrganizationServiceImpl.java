package com.octo.service.impl;

import com.alibaba.excel.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.octo.entity.Organization;
import com.octo.enums.ResponseCodeEnums;
import com.octo.exception.CustomException;
import com.octo.mapper.OrganizationMapper;
import com.octo.service.IOrganizationService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
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
    public List<Organization> listOrganizations(Organization organization, String sortField, String sortOrder) {
        // 条件构建
        LambdaQueryWrapper<Organization> queryWrapper = Wrappers.lambdaQuery(Organization.class)
                .like(StringUtils.isNotBlank(organization.getOrganizationName()), Organization::getOrganizationName, organization.getOrganizationName())
                .eq(organization.getOrganizationType() != null, Organization::getOrganizationType, organization.getOrganizationType());

        // 动态排序
        HashMap<String, SFunction<Organization, ?>> allowedSortFields = new HashMap<>();
        allowedSortFields.put("organizationName", Organization::getOrganizationName);
        allowedSortFields.put("createTime", Organization::getCreateTime);
        if (StringUtils.isNotBlank(sortField) && StringUtils.isNotBlank(sortOrder)) {
            SFunction<Organization, ?> field = allowedSortFields.get(sortField);
            if ("asc".equalsIgnoreCase(sortOrder)) {
                queryWrapper.orderByAsc(field);
            } else if ("desc".equalsIgnoreCase(sortOrder)) {
                queryWrapper.orderByDesc(field);
            }
        } else {
            queryWrapper.orderByAsc(Organization::getSortNumber);
        }

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

    @Override
    public List<Long> getOrgAndSubOrgIds(Long organizationId) {
        List<Long> result = new ArrayList<>();
        collectOrgIds(organizationId, result);
        return result;
    }

    private void collectOrgIds(Long parentId, List<Long> result) {
        // 包含当前机构
        result.add(parentId);

        // 查子机构
        LambdaQueryWrapper<Organization> queryWrapper = new LambdaQueryWrapper<Organization>()
                .eq(Organization::getParentId, parentId);
        List<Organization> children = list(queryWrapper);

        for (Organization child : children) {
            collectOrgIds(child.getId(), result); // 递归查下级
        }
    }
}
