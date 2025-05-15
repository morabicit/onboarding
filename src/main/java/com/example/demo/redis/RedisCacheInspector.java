package com.example.demo.redis;

import com.example.demo.entity.SKU;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RedisCacheInspector {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisCacheInspector(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Object getCachedProducts() {
        return redisTemplate.opsForValue().get("products");
    }
    public void setCached(String key,Object object) {
        redisTemplate.opsForValue().set(key, object);
    }

    public List<SKU> getCached(String key, TypeReference<List<SKU>> typeReference) {
        List<SKU> cached = (List<SKU>) redisTemplate.opsForValue().get(key);
        return cached;
    }
}