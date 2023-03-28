package com.example.demo.controller;

import com.example.demo.business.repository.model.UserEntity;
import com.example.demo.business.service.UserService;
import com.example.demo.model.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.example.demo.helper.HelperUser.createUserDto;
import static com.example.demo.helper.HelperUser.createUserDto2;
import static com.example.demo.helper.HelperUser.createUserEntity;
import static com.example.demo.helper.HelperUser.createUserEntity2;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerUnitTest {
    @Autowired
    private MockMvc mockMvc;
    private static final String URL = "/user";

    @MockBean
    UserService userService;

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


}


