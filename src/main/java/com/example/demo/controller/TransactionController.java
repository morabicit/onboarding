package com.example.demo.controller;

import com.example.demo.dto.SubscribeRequest;
import com.example.demo.dto.TransactionDto;
import com.example.demo.entity.Transaction;
import com.example.demo.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<List<TransactionDto>> getAllTransactionsPerUser() {
        List<TransactionDto> transactions = transactionService.getAllTransactionsPerUser();
        return ResponseEntity.ok(transactions);
    }
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<TransactionDto>> getAllTransactions() {
        List<TransactionDto> transactions = transactionService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }


}
