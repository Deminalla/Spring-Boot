package com.example.demo.controller;

import com.example.demo.business.enums.OrderStatus;
import com.example.demo.business.repository.model.OrderEntity;
import com.example.demo.business.service.OrderService;
import com.example.demo.model.OrderDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    OrderDto orderDto;
    OrderEntity orderEntity;
    CreateObjects helperClass = new CreateObjects();

    @BeforeEach
    void init() {
        orderDto = helperClass.createOrderDto();
        orderEntity = helperClass.createOrderEntity();
    }

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


    @Test
    void changeOrderStatus_Successful() throws Exception {
        when(orderService.findOrderById(orderDto.getId())).thenReturn(Optional.of(orderDto));
        OrderDto orderDto2 = helperClass.createOrderDto2();
        when(orderService.changeStatus(orderDto, OrderStatus.IN_PROGRESS)).thenReturn(orderDto2);

        RequestBuilder request = MockMvcRequestBuilders
                .put(URL + "/" + orderDto.getId() + "/" + "IN_PROGRESS")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andDo(print());
    }
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
}
