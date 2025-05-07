package com.example.demo.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class PaymentMethodDto {
    private Long userId;

    @NotNull
    private String cardType;

    @NotBlank
    private String cardNumber;

    @NotBlank
    private String issuerCountry;

    @NotBlank
    private String expiryDate;
}

