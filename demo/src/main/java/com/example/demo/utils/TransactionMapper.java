package com.example.demo.utils;

import com.example.demo.dto.TransactionDto;
import com.example.demo.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mapping(source = "user.email", target = "userEmail")
    @Mapping(source = "subscription.sku.sku", target = "subscriptionPlan")
    @Mapping(source = "paymentMethod.maskedCardNumber", target = "paymentCardNumber")
    TransactionDto toDto(Transaction transaction);
}

