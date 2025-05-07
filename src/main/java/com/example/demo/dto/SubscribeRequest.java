package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SubscribeRequest {
    @NotBlank(message = "Invalid request")
    private Long skuId;
    @NotBlank(message = "Invalid request")
    private Long paymentMethodId;
}
