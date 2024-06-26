package com.octo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.octo.entity.Account;
import com.octo.util.Response;

import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zms
 * @since 2023-11-23
 */
public interface IAccountService extends IService<Account> {

    /**
     * 获取用户信息、菜单、角色
     *
     * @param accountName
     * @return
     */
    Response getAccount(String accountName);

    /**
     * 获取账户列表
     *
     * @param page
     * @param limit
     * @return
     */
    Map<String, Object> getAccountPage(int page, int limit);
}
