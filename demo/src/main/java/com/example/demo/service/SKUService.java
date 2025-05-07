package com.example.demo.service;

import com.example.demo.entity.SKU;
import com.example.demo.entity.Subscription;
import com.example.demo.repository.SKURepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SKUService {

    private final SKURepository skuRepository;
    public SKUService(SKURepository skuRepository) {
        this.skuRepository = skuRepository;
    }
    public List<SKU> getAllProducts() {
        return skuRepository.findAll();
    }
}
