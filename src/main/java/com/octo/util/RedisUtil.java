package com.octo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // 设置值
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    // 设置值并指定过期时间
    public void set(String key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    // 获取值
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // 删除键
    public boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    // 设置过期时间
    public boolean expire(String key, long timeout, TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    // 检查键是否存在
    public boolean hashKey(String key) {
        return redisTemplate.hasKey(key);
    }
}
