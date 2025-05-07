package com.example.demo.exception;

public class InvalidPaymentMethod extends RuntimeException {
    public InvalidPaymentMethod(String message) {
        super(message);
    }
}
