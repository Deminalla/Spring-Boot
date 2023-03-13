package com.example.demo.business.service;

import com.example.demo.model.NewUserDto;
import com.example.demo.model.UserDto;
import com.example.demo.model2.ClientDto;

import java.math.BigDecimal;
import java.util.Optional;

public interface UserService {
    ClientDto getClientName(long id);

    UserDto extractMoney(UserDto user, BigDecimal money);

    Optional<UserDto> findUserByUsername(String username);

    void checkConfirmationCode(UserDto user, int code);

    boolean enoughBalance(UserDto user, BigDecimal price);

    UserDto createNewUser(NewUserDto newUserDto);

}
