package com.octo.shiro;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.octo.entity.User;
import com.octo.service.IUserService;
import com.octo.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;

/**
 * @author zms
 */
@Slf4j
public class MyRealm extends AuthorizingRealm {
    @Resource
    private IUserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        MyRealm.log.info("授权");
        return null;
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        if (token instanceof JwtToken) {
            return true;
        }
        return false;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = (String) authenticationToken.getPrincipal();
        if (!JwtUtil.validateToken(token)) {
            throw new AuthenticationException("token校验失败！");
        }
        User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, JwtUtil.getUsername(token)));
        if (null == user) {
            throw new AccountException("该用户不存在！");
        }
        return new SimpleAuthenticationInfo(user, token, getName());
    }
}
