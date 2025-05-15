package com.example.demo.utils;

import com.example.demo.dto.TransactionDto;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.Transaction;
import com.example.demo.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "dateOfBirth", target = "dateOfBirth")
    @Mapping(source = "mobileNumber", target = "mobileNumber")
    @Mapping(source = "role", target = "role")
    User toEntity(UserDto userDto);
}

