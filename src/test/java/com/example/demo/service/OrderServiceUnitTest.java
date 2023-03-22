package com.example.demo.service;

import com.example.demo.business.enums.OrderStatus;
import com.example.demo.business.mapper.OrderEntityMapStruct;
import com.example.demo.business.repository.OrderRepository;
import com.example.demo.business.repository.model.OrderEntity;
import com.example.demo.business.service.implementation.OrderServiceImpl;
import com.example.demo.model.OrderDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.example.demo.helper.HelperOrder.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceUnitTest {
    @Mock
    OrderRepository orderRepository;
    @Mock
    OrderEntityMapStruct orderMapper;
    @InjectMocks
    OrderServiceImpl orderService;
    OrderDto orderDto;
    OrderEntity orderEntity;

    @BeforeEach
    void init() {
        orderDto = createOrderDto();
        orderEntity = createOrderEntity();
    }

    @Test
    void findOrderById(){
        when(orderRepository.findById(orderDto.getId())).thenReturn(Optional.ofNullable(orderEntity));
        when(orderMapper.entityToDto(orderEntity)).thenReturn(orderDto);
        Optional<OrderDto> orderById = orderService.findOrderById(orderDto.getId());

        assertTrue(orderById.isPresent());
        assertEquals(orderById.get(), orderDto);
    }
    @Test
    void findOrderByNr_NotFound(){
        when(orderRepository.findById(orderDto.getId())).thenReturn(Optional.empty());
        Optional<OrderDto> orderById = orderService.findOrderById(orderDto.getId());

        assertFalse(orderById.isPresent());
    }

    @Test
    void findByStatus_IN_PROGRESS(){
        OrderDto orderDto1 = createOrderDto2();
        OrderDto orderDto2 = createOrderDto3();
        List<OrderDto> orderDtoList = Arrays.asList(orderDto1, orderDto2);

        OrderEntity orderEntity1 = createOrderEntity2();
        OrderEntity orderEntity2 = createOrderEntity3();
        List<OrderEntity> orderEntityList = Arrays.asList(orderEntity1, orderEntity2);

        when(orderRepository.findAllByStatusEquals(OrderStatus.IN_PROGRESS)).thenReturn(orderEntityList);
        when(orderMapper.entityToDto(orderEntity1)).thenReturn(orderDto1);
        when(orderMapper.entityToDto(orderEntity2)).thenReturn(orderDto2);

        assertEquals(orderDtoList, orderService.findOrdersByStatus(OrderStatus.IN_PROGRESS));
    }

    @Test
    void changeStatus_ToFINISHED(){
        when(orderMapper.dtoToEntity(orderDto)).thenReturn(orderEntity);

        orderEntity.setStatus(OrderStatus.FINISHED);
        when(orderRepository.save(orderEntity)).thenReturn(orderEntity);

        OrderDto order = createOrderDto();
        order.setStatus(OrderStatus.FINISHED);
        when(orderMapper.entityToDto(orderEntity)).thenReturn(order);
        OrderDto orderChangedStatus =  orderService.changeStatus(orderDto, OrderStatus.FINISHED);

        assertEquals(orderChangedStatus.getStatus(), OrderStatus.FINISHED);
        assertNotEquals(orderChangedStatus.getStatus(), OrderStatus.NOT_STARTED);
    }
}
