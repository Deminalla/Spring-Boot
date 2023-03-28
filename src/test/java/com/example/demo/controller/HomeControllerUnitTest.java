package com.example.demo.controller;

import com.example.demo.business.repository.model.UserEntity;
import com.example.demo.business.service.UserService;
import com.example.demo.model.NewUserDto;
import com.example.demo.model.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.server.ResponseStatusException;

import static com.example.demo.helper.HelperClass.asJsonString;
import static com.example.demo.helper.HelperUser.createNewUserDto;
import static com.example.demo.helper.HelperUser.createUserDto;
import static com.example.demo.helper.HelperUser.createUserDto2;
import static com.example.demo.helper.HelperUser.createUserEntity;
import static com.example.demo.helper.HelperUser.createUserEntity2;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@WithAnonymousUser
public class HomeControllerUnitTest {
    @Autowired
    private MockMvc mockMvc;
    private static final String URL = "";

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

    @Test
    void createUser_Success() throws Exception{
        NewUserDto newUserDto = createNewUserDto();
        when(userService.createNewUser(newUserDto)).thenReturn(userDto);

        ResultActions mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post(URL + "/new_user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newUserDto)))
                .andExpect(status().isCreated());
    }
    @Test
    void createUser_UsernameTaken() throws Exception{
        NewUserDto newUserDto = createNewUserDto();
        doThrow(new ResponseStatusException(HttpStatus.CONFLICT)).when(userService).createNewUser(newUserDto);

        ResultActions mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post(URL + "/new_user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newUserDto)))
                .andExpect(status().isConflict());
    }
}
