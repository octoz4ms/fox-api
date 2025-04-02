package com.octo.service.impl;

import com.alibaba.excel.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
    public void addOrganization(Organization organization) {
        boolean save = save(organization);
        if (!save) {
            throw new CustomException(ResponseCodeEnums.FAIL);
        }
    }

    @Override
    public void updateOrganization(Organization organization) {
        boolean update = updateById(organization);
        if (!update) {
            throw new CustomException(ResponseCodeEnums.FAIL);
        }
    }

    @Override
    public void deleteOrganization(Long id) {
        boolean remove = removeById(id);
        if (!remove) {
            throw new CustomException(ResponseCodeEnums.FAIL);
        }
    }
}
