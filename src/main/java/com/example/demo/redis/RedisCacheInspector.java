package com.example.demo.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisCacheInspector {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisCacheInspector(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Object getCachedProducts() {
        return redisTemplate.opsForValue().get("products");
    }
}

