package com.octo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.octo.entity.UserRole;
import com.octo.mapper.UserRoleMapper;
import com.octo.service.IUserRoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 账户角色表 服务实现类
 * </p>
 *
 * @author zms
 * @since 2023-11-23
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

}
