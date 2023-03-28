package com.example.demo.controller;

import com.example.demo.business.enums.OrderStatus;
import com.example.demo.business.repository.model.OrderEntity;
import com.example.demo.business.repository.model.UserEntity;
import com.example.demo.business.service.OrderService;
import com.example.demo.business.service.UserService;
import com.example.demo.model.OrderDto;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.demo.helper.HelperOrder.createOrderDto;
import static com.example.demo.helper.HelperOrder.createOrderDto2;
import static com.example.demo.helper.HelperOrder.createOrderEntity;
import static com.example.demo.helper.HelperUser.createUserDto;
import static com.example.demo.helper.HelperUser.createUserEntity;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class OrderControllerUnitTest {
    @Autowired
    private MockMvc mockMvc;
    private static final String URL = "/order";

    @MockBean
    OrderService orderService;
    @MockBean
    UserService userService;

    OrderDto orderDto;
    OrderEntity orderEntity;
    UserDto userDto;
    UserEntity userEntity;

    @BeforeEach
    void init() {
        orderDto = createOrderDto();
        orderEntity = createOrderEntity();
        userDto = createUserDto();
        userEntity = createUserEntity();
    }

    @WithMockUser(roles = {"USER", "COMPANY"})
    @Test
    void getOrdersByStatus() throws Exception {
        List<OrderDto> orderList = new ArrayList<>();
        orderList.add(orderDto);
        when(orderService.findOrdersByStatus(orderDto.getStatus())).thenReturn(orderList);

        RequestBuilder request = MockMvcRequestBuilders
                .get(URL + "/status" + "/" + orderDto.getStatus().toString())
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andDo(print());
    }

    @WithMockUser(username="1", roles= "COMPANY")
    @Test
    void changeOrderStatus_Successful() throws Exception {
        when(orderService.findOrderById(orderDto.getId())).thenReturn(Optional.of(orderDto));
        OrderDto orderDto2 = createOrderDto2();
        when(orderService.changeStatus(orderDto, OrderStatus.IN_PROGRESS, orderDto.getCompanyId().toString())).thenReturn(orderDto2);

        RequestBuilder request = MockMvcRequestBuilders
                .put(URL + "/" + orderDto.getId() + "/" + "IN_PROGRESS")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andDo(print());
    }

    @WithMockUser(username = "1", roles = "COMPANY")
    @Test
    void changeOrderStatus_NotFound() throws Exception {
        when(orderService.findOrderById(orderDto.getId())).thenReturn(Optional.empty());

        RequestBuilder request = MockMvcRequestBuilders
                .put(URL + "/" + orderDto.getId() + "/" + "IN_PROGRESS")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andDo(print());
    }
    @WithMockUser(username = "2", roles = "COMPANY")
    @Test
    void changeOrderStatus_WrongCompany() throws Exception {
        when(orderService.findOrderById(orderDto.getId())).thenReturn(Optional.of(orderDto));
        doThrow(new ResponseStatusException(HttpStatus.UNAUTHORIZED)).when(orderService).changeStatus(orderDto, OrderStatus.IN_PROGRESS, "2");

        RequestBuilder request = MockMvcRequestBuilders
                .put(URL + "/" + orderDto.getId() + "/" + "IN_PROGRESS")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }
    @WithMockUser(username = "User1", roles = "USER")
    @Test
    void changeOrderStatus_WrongRole() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .put(URL + "/" + orderDto.getId() + "/" + "IN_PROGRESS")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @WithMockUser(username = "User1", roles = "USER")
    @Test
    void createOrder_Successful() throws Exception {
        when(userService.findUserByUsername(userDto.getUsername())).thenReturn(Optional.ofNullable(userDto));
        doNothing().when(userService).checkConfirmationCode(userDto, userDto.getCode());
        when(orderService.createOrder(userDto, orderDto.getPrice())).thenReturn(orderDto);

        RequestBuilder request = MockMvcRequestBuilders
                .post(URL + "/new_order")
                .param("username", userDto.getUsername())
                .param("code", userDto.getCode().toString())
                .param("price", orderDto.getPrice().toString())
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andDo(print());
    }
    @WithMockUser(username = "User1", roles = "USER")
    @Test
    void createOrder_WrongCode() throws Exception {
        when(userService.findUserByUsername(userDto.getUsername())).thenReturn(Optional.ofNullable(userDto));
        doThrow(new ResponseStatusException(HttpStatus.UNAUTHORIZED)).when(userService).checkConfirmationCode(userDto, userDto.getCode());

        RequestBuilder request = MockMvcRequestBuilders
                .post(URL + "/new_order")
                .param("username", userDto.getUsername())
                .param("code", userDto.getCode().toString())
                .param("price", orderDto.getPrice().toString())
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }
    @WithMockUser(username = "User1", roles = "USER")
    @Test
    void createOrder_NotEnoughBalance() throws Exception {
        when(userService.findUserByUsername(userDto.getUsername())).thenReturn(Optional.ofNullable(userDto));
        doNothing().when(userService).checkConfirmationCode(userDto, userDto.getCode());
        doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST)).when(orderService).createOrder(userDto, orderDto.getPrice());

        RequestBuilder request = MockMvcRequestBuilders
                .post(URL + "/new_order")
                .param("username", userDto.getUsername())
                .param("code", userDto.getCode().toString())
                .param("price", orderDto.getPrice().toString())
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @WithMockUser(username = "1", roles = "COMPANY")
    @Test
    void acceptOrder_Successful() throws Exception {
        when(orderService.findOrderById(orderDto.getId())).thenReturn(Optional.of(orderDto));
        doNothing().when(orderService).acceptOrder(orderDto, orderDto.getCompanyId().toString());

        RequestBuilder request = MockMvcRequestBuilders
                .put(URL + "/accept/" + orderDto.getId())
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andDo(print());
    }
    @WithMockUser(username = "2", roles = "COMPANY")
    @Test
    void acceptOrder_AlreadyTaken() throws Exception {
        when(orderService.findOrderById(orderDto.getId())).thenReturn(Optional.of(orderDto));
        doThrow(new ResponseStatusException(HttpStatus.CONFLICT)).when(orderService).acceptOrder(orderDto, "2");

        RequestBuilder request = MockMvcRequestBuilders
                .put(URL + "/accept/" + orderDto.getId())
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isConflict())
                .andDo(print());
    }
}
