package com.example.demo.business.service.implementation;

import com.example.demo.business.enums.OrderStatus;
import com.example.demo.business.mapper.OrderEntityMapStruct;
import com.example.demo.business.repository.OrderRepository;
import com.example.demo.business.repository.model.OrderEntity;
import com.example.demo.business.service.OrderService;
import com.example.demo.business.service.UserService;
import com.example.demo.model.OrderDto;
import com.example.demo.model.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderEntityMapStruct orderMapper;
    private final UserService userService;

    @Override
    public Optional<OrderDto> findOrderById(long oderId) {
        Optional<OrderEntity> orderById = orderRepository.findById(oderId);
        Optional<OrderDto> orderDtoById = orderById.flatMap(order -> Optional.ofNullable(orderMapper.entityToDto(order)));
        log.info("Order with id {} is {}", oderId, orderDtoById);

        return orderDtoById;
    }

    @Override
    public List<OrderDto> findOrdersByStatus(OrderStatus status) {
        List<OrderEntity> orderListByStatus= orderRepository.findAllByStatusEquals(status);
        log.info("Orders with status {} are {}",status, orderListByStatus);

        return orderListByStatus.stream().map(orderMapper::entityToDto).collect(Collectors.toList());
    }

    @Override
    public OrderDto changeStatus(OrderDto order, OrderStatus status) {
        OrderEntity orderEntity = orderMapper.dtoToEntity(order);
        log.info("Change order status to {}, for order {}", status, order);
        orderEntity.setStatus(status);
        orderRepository.save(orderEntity);

        return orderMapper.entityToDto(orderEntity);
    }

    @Override
    public OrderDto createOrder(UserDto user, BigDecimal price) {
        userService.extractMoney(user, price);

        log.info("Creating a new order");
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setUserId(user.getId());
        orderEntity.setDate(LocalDateTime.now());
        orderEntity.setPrice(price);
        orderEntity.setStatus(OrderStatus.NOT_STARTED);
        orderRepository.save(orderEntity);
        OrderDto orderDto = orderMapper.entityToDto(orderEntity);
        log.info("New order {} has been created", orderDto);

        return orderDto;
    }

}
