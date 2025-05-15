package com.example.demo.dto;

import com.example.demo.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserDto {
    Long id;
    String email;
    String fullName;
    LocalDate dateOfBirth;
    String mobileNumber;
    String role;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password;

    public static List<UserDto> userToUserDto(List<User> users) {
        return users.stream().map(UserDto::userToUserDto).collect(Collectors.toList());
    }
    public static UserDto userToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFullName(user.getFullName());
        userDto.setEmail(user.getEmail());
        userDto.setDateOfBirth(user.getDateOfBirth());
        userDto.setMobileNumber(user.getMobileNumber());
        userDto.setRole(String.valueOf(user.getRole()));
        return userDto;
    }
}
