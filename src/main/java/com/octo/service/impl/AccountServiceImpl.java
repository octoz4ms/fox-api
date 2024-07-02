package com.octo.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.octo.entity.Account;
import com.octo.mapper.AccountMapper;
import com.octo.service.IAccountService;
import com.octo.service.IMenuService;
import com.octo.service.IRoleService;
import com.octo.util.ApiResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zms
 * @since 2023-11-23
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements IAccountService {

    @Resource
    private IRoleService roleService;
    @Resource
    private IMenuService menuService;

    @Override
    public ApiResponse getAccount(String accountName) {
        Account account = getOne(new LambdaQueryWrapper<Account>().eq(Account::getAccountName, accountName));
        if (ObjectUtil.isEmpty(account)) {
            return ApiResponse.fail("用户名错误");
        }
        // 隐藏密码
        account.setPassword(null);

        return ApiResponse.success(account);
    }

    @Override
    public Map<String, Object> getAccountPage(int page, int limit) {
        Page<Account> accountPage = page(new Page<>(page, limit));
        HashMap<String, Object> result = new HashMap<>(2);
        System.out.println(accountPage);
        result.put("count", accountPage.getTotal());
        result.put("list", accountPage.getRecords());
        return result;
    }
}
