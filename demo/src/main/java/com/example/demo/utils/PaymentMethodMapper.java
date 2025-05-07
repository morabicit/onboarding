package com.example.demo.utils;

import com.example.demo.dto.PaymentMethodDto;
import com.example.demo.entity.PaymentMethod;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMethodMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "cardType", target = "cardType")
    @Mapping(source = "maskedCardNumber", target = "cardNumber")
    PaymentMethodDto toDto(PaymentMethod paymentMethod);
}