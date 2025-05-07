package com.example.demo.controller;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.SKU;
import com.example.demo.service.SKUService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/sku")
public class SKUController {
    private final SKUService skuService;

    public SKUController(SKUService skuService) {
        this.skuService = skuService;
    }

    @GetMapping
    public ResponseEntity<List<SKU>> getAllSKUs() {
        List<SKU> products = skuService.getAllProducts();
        return ResponseEntity.ok(products);
    }
}
