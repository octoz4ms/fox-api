package com.octo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.octo.entity.User;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author zms
 * @since 2024-07-02
 */
public interface IUserService extends IService<User> {
    User getUserByUsername(String username);
}
