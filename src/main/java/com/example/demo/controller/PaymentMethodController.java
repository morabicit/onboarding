package com.example.demo.controller;

import com.example.demo.dto.PaymentMethodDto;
import com.example.demo.entity.PaymentMethod;
import com.example.demo.service.PaymentMethodService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pms")
public class PaymentMethodController {

    private final PaymentMethodService paymentMethodService;

    public PaymentMethodController(PaymentMethodService paymentMethodService) {
        this.paymentMethodService = paymentMethodService;
    }
    @GetMapping
    public List<PaymentMethodDto> getUserPaymentMethods() {
        return paymentMethodService.getUserPaymentMethods();
    }

    @PostMapping("/addPM")
    public ResponseEntity<PaymentMethod> addPaymentMethod(
            @Valid @RequestBody PaymentMethodDto pmDto) {
        PaymentMethod paymentMethod = paymentMethodService.addPaymentMethod(pmDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentMethod);
    }
}

