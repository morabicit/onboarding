package com.example.demo.service;

import com.example.demo.entity.SKU;
import com.example.demo.redis.RedisCacheInspector;
import com.example.demo.repository.SKURepository;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class SKUService {

    private final SKURepository skuRepository;
    private final RedisCacheInspector redisCacheInspector;

    public SKUService(SKURepository skuRepository, RedisCacheInspector redisCacheInspector) {
        this.skuRepository = skuRepository;
        this.redisCacheInspector = redisCacheInspector;
    }

    public List<SKU> getAllProducts() {
        // Try to get from cache
        List<SKU> cachedProducts = redisCacheInspector.getCached("products", new TypeReference<List<SKU>>() {});

        if (cachedProducts != null && !cachedProducts.isEmpty()) {
            return cachedProducts;
        }
        List<SKU> products = skuRepository.findAll();
        redisCacheInspector.setCached("products", products);
        return products;
    }
}
