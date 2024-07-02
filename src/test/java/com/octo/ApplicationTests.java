package com.octo;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.octo.entity.Role;
import com.octo.service.IRoleService;
import com.octo.service.IUserRoleService;
import com.octo.util.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class ApplicationTests {

    @Autowired
    private RedisUtil redisUtil;

    @Resource
    private IRoleService roleService;

    @Resource
    private IUserRoleService userRoleService;

    @Test
    void contextLoads() {
//        List<String> roleNos = userRoleService.list(Wrappers.lambdaQuery(UserRole.class)
//                .select(UserRole::getRoleNo)).stream().map(r -> r.getRoleNo()).collect(Collectors.toList());
//        System.out.println("roleNos:" + roleNos);
        roleService.list(Wrappers.lambdaQuery(Role.class).in(Role::getRoleNo, List.of()));
    }

}
