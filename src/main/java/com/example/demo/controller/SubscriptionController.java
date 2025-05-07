package com.example.demo.controller;

import com.example.demo.dto.SubscribeRequest;
import com.example.demo.entity.SKU;
import com.example.demo.entity.Subscription;
import com.example.demo.service.SKUService;
import com.example.demo.service.SubscriptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sub")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @GetMapping
    public ResponseEntity<Subscription> getSubPerUser() {
        Subscription products = subscriptionService.getSubPerUser();
        return ResponseEntity.ok(products);
    }
    @PostMapping("/subscribe")
    public ResponseEntity<String> subscribe(@RequestBody SubscribeRequest request) {
        String result = subscriptionService.subscribe(
                request.getSkuId(),
                request.getPaymentMethodId()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PatchMapping("/update/{sku_id}")
    public ResponseEntity<String> update(@PathVariable Long sku_id) {
        return ResponseEntity.ok(subscriptionService.update(sku_id));
    }

    @PatchMapping("/cancel")
    public ResponseEntity<?> cancel() {
        subscriptionService.cancel();
        return ResponseEntity.noContent().build();
    }
}
