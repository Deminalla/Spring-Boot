package com.example.demo.business.service;


import com.example.demo.business.enums.OrderStatus;
import com.example.demo.model.OrderDto;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<OrderDto> findOrdersByPaidOrNot(boolean paid);

    Optional<OrderDto> findOrderByNr(long number);

    OrderDto changeStatus (OrderDto order, OrderStatus status);
}
