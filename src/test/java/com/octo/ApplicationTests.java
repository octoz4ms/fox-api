package com.octo;

import com.octo.util.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

@SpringBootTest
class ApplicationTests {

    @Autowired
    private RedisUtil redisUtil;

    @Test
    void contextLoads() {
        redisUtil.set("order:01", "cup", 5, TimeUnit.SECONDS);
        System.out.println(redisUtil.get("order:01"));
    }

}
