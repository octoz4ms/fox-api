package com.octo;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.octo.entity.User;
import com.octo.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class ApplicationTests {
    @Autowired
    private IUserService userService;

    @Test
    void contextLoads() {
        LambdaUpdateWrapper<User> updateWrapper = Wrappers.lambdaUpdate(User.class).eq(User::getId, 2L).set(User::getUsername, "user");
        boolean update = userService.update(updateWrapper);
        System.out.println(update);
    }

}
