package com.example.demo.Helper;

import com.example.demo.business.repository.model.UserEntity;
import com.example.demo.model.UserDto;

import java.math.BigDecimal;

public class HelperUser {

    public UserDto createUserDto(){
        UserDto user = new UserDto();
        user.setUsername("User1");
        user.setEmail("user1@gmail.com");
        user.setPassword("passwordStrong");
        user.setCode(1426);
        user.setBalance(BigDecimal.TEN);
        return user;
    }
    public UserEntity createUserEntity(){
        UserEntity user = new UserEntity();
        user.setUsername("User1");
        user.setEmail("user1@gmail.com");
        user.setPassword("passwordStrong");
        user.setCode(1426);
        user.setBalance(BigDecimal.TEN);
        return user;
    }

    public UserDto createUserDto2(){
        UserDto user = new UserDto();
        user.setUsername("User1");
        user.setEmail("user1@gmail.com");
        user.setPassword("passwordStrong");
        user.setCode(1426);
        user.setBalance(BigDecimal.ZERO);
        return user;
    }

    public UserEntity createUserEntity2(){
        UserEntity user = new UserEntity();
        user.setUsername("User1");
        user.setEmail("user1@gmail.com");
        user.setPassword("passwordStrong");
        user.setCode(1426);
        user.setBalance(BigDecimal.ZERO);
        return user;
    }
}
