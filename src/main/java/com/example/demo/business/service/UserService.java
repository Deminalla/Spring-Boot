package com.example.demo.business.service;

import com.example.demo.model.NewUserDto;
import com.example.demo.model.UserDto;
import com.example.demo.model2.ClientDto;

import java.math.BigDecimal;
import java.util.Optional;

public interface UserService {
    ClientDto getClientName(long id);

    BigDecimal modifyBalance(String user, BigDecimal money, boolean add);

    Optional<UserDto> findUserByUsername(String username);

    void checkConfirmationCode(UserDto user, int code);

    boolean enoughBalance(UserDto user, BigDecimal price);

    UserDto createNewUser(NewUserDto newUserDto);

}
