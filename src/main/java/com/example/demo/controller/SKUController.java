package com.example.demo.controller;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.SKU;
import com.example.demo.redis.RedisCacheInspector;
import com.example.demo.redis.RedisTemplateConfig;
import com.example.demo.service.SKUService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/sku")
public class SKUController {
    private final SKUService skuService;
    private final RedisCacheInspector redisCacheInspector;
    public SKUController(SKUService skuService,RedisCacheInspector redisCacheInspector) {
        this.skuService = skuService;
        this.redisCacheInspector = redisCacheInspector;
    }

    @GetMapping
    public ResponseEntity<List<SKU>> getAllSKUs() {
        List<SKU> products = skuService.getAllProducts();
        return ResponseEntity.ok(products);
    }
    @GetMapping("/testCache")
    public ResponseEntity<String> testCache() {
         Object redis = redisCacheInspector.getCachedProducts();
         return ResponseEntity.ok(redis == null ? "null" : redis.toString());
    }
}
