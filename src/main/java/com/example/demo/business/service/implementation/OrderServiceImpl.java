package com.example.demo.business.service.implementation;

import com.example.demo.business.enums.OrderStatus;
import com.example.demo.business.mapper.OrderEntityMapStruct;
import com.example.demo.business.repository.OrderRepository;
import com.example.demo.business.repository.model.OrderEntity;
import com.example.demo.business.service.OrderService;
import com.example.demo.model.OrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderEntityMapStruct orderMapper;

    @Override
    public List<OrderDto> findOrdersByPaidOrNot(boolean paid) {
        short paidShort = (short) (paid ? 1 : 0);
        List<OrderEntity> orderListByPaid = orderRepository.findAllByPaidEquals(paidShort);

        return orderListByPaid.stream().map(orderMapper::entityToDto).collect(Collectors.toList());
    }

    @Override
    public Optional<OrderDto> findOrderByNr(long number) {
        Optional<OrderEntity> orderById = orderRepository.findById(number);
        Optional<OrderDto> orderDtoById = orderById.flatMap(order -> Optional.ofNullable(orderMapper.entityToDto(order)));
        log.info("Order with id {} is {}", number, orderDtoById);
        return orderDtoById;
    }

    @Override
    public OrderDto changeStatus(OrderDto order, OrderStatus status) {
        OrderEntity orderEntity = orderMapper.dtoToEntity(order);
        log.info("Change order status to {}, for order {}", status, order);
        orderEntity.setStatus(status);
        orderRepository.save(orderEntity);
        return orderMapper.entityToDto(orderEntity);
    }
}
