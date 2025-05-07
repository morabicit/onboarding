package com.example.demo.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TransactionDto {
    private Long id;
    private String userEmail;
    private String subscriptionPlan;
    private double amount;
    private LocalDateTime transactionDate;
    private String description;
    private String paymentCardNumber;
}

