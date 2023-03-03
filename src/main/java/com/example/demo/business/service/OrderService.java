package com.example.demo.business.service;


import com.example.demo.business.enums.OrderStatus;
import com.example.demo.model.OrderDto;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Optional<OrderDto> findOrderById(long orderId);

    List<OrderDto> findOrdersByStatus(OrderStatus status);

    OrderDto changeStatus (OrderDto order, OrderStatus status);
}
