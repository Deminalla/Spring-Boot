package com.example.demo.controller;

import com.example.demo.business.repository.model.UserEntity;
import com.example.demo.business.service.UserService;
import com.example.demo.model.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

import static com.example.demo.helper.HelperUser.createUserDto;
import static com.example.demo.helper.HelperUser.createUserDto2;
import static com.example.demo.helper.HelperUser.createUserEntity;
import static com.example.demo.helper.HelperUser.createUserEntity2;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @WithMockUser(username="User1", roles= "USER")
    @Test
    void addMoney_Success() throws Exception {
        doNothing().when(userService).modifyBalance(userDto.getUsername(), BigDecimal.ONE, true);

        RequestBuilder request = MockMvcRequestBuilders
                .put(URL + "/add_money")
                .param("money", String.valueOf(BigDecimal.ONE))
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andDo(print());
    }
    @WithMockUser(username="User1", roles= "USER")
    @Test
    void addMoney_InvalidNumber() throws Exception {
        doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST)).when(userService).modifyBalance(userDto.getUsername(), BigDecimal.valueOf(-1), true);

        RequestBuilder request = MockMvcRequestBuilders
                .put(URL + "/add_money")
                .param("money", String.valueOf(BigDecimal.valueOf(-1)))
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}


