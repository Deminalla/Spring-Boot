package com.example.demo.service;

import com.example.demo.business.mapper.UserEntityMapStruct;
import com.example.demo.business.repository.UserRepository;
import com.example.demo.business.repository.model.UserEntity;
import com.example.demo.business.service.implementation.UserServiceImpl;
import com.example.demo.model.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Optional;

import static com.example.demo.helper.HelperUser.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTest {
    @Mock
    UserRepository userRepository;
    @Mock
    UserEntityMapStruct userMapper;
    @Mock
    RestTemplate restTemplate;
    @InjectMocks
    UserServiceImpl userService;

    UserDto userDto;
    UserEntity userEntity;
    UserDto userDto2;
    UserEntity userEntity2;


    @BeforeEach
    void init() {
        userDto = createUserDto();
        userEntity = createUserEntity();
        userDto2 = createUserDto2();
        userEntity2 = createUserEntity2();
    }

    @Test
    void extractMoney (){
        when(userMapper.dtoToEntity(userDto2)).thenReturn(userEntity2);
        UserDto user = userService.extractMoney(userDto, BigDecimal.TEN);
        assertEquals(userDto2, user);
        assertEquals(userDto2.getBalance(), user.getBalance());
    }

    @Test
    void findUserByUsername_Successful(){
        when(userRepository.findByUsername(userEntity.getUsername())).thenReturn(Optional.of(userEntity));
        when(userMapper.entityToDto(userEntity)).thenReturn(userDto);
        Optional<UserDto> user = userService.findUserByUsername(userEntity.getUsername());
        assertTrue(user.isPresent());
    }
    @Test
    void findUserByUsername_NotFound(){
        when(userRepository.findByUsername(userEntity.getUsername())).thenReturn(Optional.empty());
        Optional<UserDto> user = userService.findUserByUsername(userEntity.getUsername());
        assertFalse(user.isPresent());
        assertEquals(Optional.empty(), user);
    }

    @Test
    void checkConfirmationCode_Successful(){
        assertDoesNotThrow(() -> userService.checkConfirmationCode(userDto, userDto.getCode()));
    }
    @Test
    void checkConfirmationCode_Fail(){
        assertThrows(ResponseStatusException.class, ()-> userService.checkConfirmationCode(userDto, 1));
    }

    @Test
    void enoughBalance_True(){
        assertTrue(userService.enoughBalance(userDto, BigDecimal.ONE));
    }

    @Test
    void enoughBalance_False(){
        assertThrows(ResponseStatusException.class, ()-> userService.enoughBalance(userDto, BigDecimal.valueOf(20)));
    }

}
