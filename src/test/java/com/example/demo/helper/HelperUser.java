package com.example.demo.helper;

import com.example.demo.business.repository.model.UserEntity;
import com.example.demo.model.NewUserDto;
import com.example.demo.model.UserDto;

import java.math.BigDecimal;

public class HelperUser {

    public static UserDto createUserDto(){
        UserDto user = new UserDto();
        user.setUsername("User1");
        user.setEmail("user1@gmail.com");
        user.setPassword("passwordStrong");
        user.setCode(1426);
        user.setBalance(BigDecimal.TEN);
        return user;
    }
    public static UserEntity createUserEntity(){
        UserEntity user = new UserEntity();
        user.setUsername("User1");
        user.setEmail("user1@gmail.com");
        user.setPassword("passwordStrong");
        user.setCode(1426);
        user.setBalance(BigDecimal.TEN);
        return user;
    }

    public static UserDto createUserDto2(){
        UserDto user = new UserDto();
        user.setUsername("User1");
        user.setEmail("user1@gmail.com");
        user.setPassword("passwordStrong");
        user.setCode(1426);
        user.setBalance(BigDecimal.ZERO);
        return user;
    }

    public static UserEntity createUserEntity2(){
        UserEntity user = new UserEntity();
        user.setUsername("User1");
        user.setEmail("user1@gmail.com");
        user.setPassword("passwordStrong");
        user.setCode(1426);
        user.setBalance(BigDecimal.ZERO);
        return user;
    }

    public static NewUserDto createNewUserDto(){
        NewUserDto user = new NewUserDto();
        user.setUsername("User1");
        user.setEmail("user1@gmail.com");
        user.setPassword("passwordStrong");
        return user;
    }
}
