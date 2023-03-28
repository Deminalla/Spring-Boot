package com.example.demo.helper;

import com.example.demo.business.enums.OrderStatus;
import com.example.demo.business.repository.model.OrderEntity;
import com.example.demo.model.OrderDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class HelperOrder {

    public static OrderDto createOrderDto(){
        OrderDto order = new OrderDto();
        order.setId(1L);
        order.setUserId(1L);
        order.setDate(LocalDateTime.parse("2022-12-09T18:19:26.492745400"));
        order.setPrice(BigDecimal.ONE);
        order.setCompanyId(1L);
        order.setStatus(OrderStatus.NOT_STARTED);
        return order;
    }
    public static OrderEntity createOrderEntity(){
        OrderEntity order = new OrderEntity();
        order.setId(1L);
        order.setUserId(1L);
        order.setDate(LocalDateTime.parse("2022-12-09T18:19:26.492745400"));
        order.setPrice(BigDecimal.ONE);
        order.setCompanyId(1L);
        order.setStatus(OrderStatus.NOT_STARTED);
        return order;
    }

    public static OrderDto createOrderDto2(){
        OrderDto order = new OrderDto();
        order.setId(1L);
        order.setUserId(1L);
        order.setDate(LocalDateTime.parse("2022-12-09T18:19:26.492745400"));
        order.setPrice(BigDecimal.ONE);
        order.setCompanyId(1L);
        order.setStatus(OrderStatus.IN_PROGRESS);
        return order;
    }
    public static OrderEntity createOrderEntity2(){
        OrderEntity order = new OrderEntity();
        order.setId(1L);
        order.setUserId(1L);
        order.setDate(LocalDateTime.parse("2022-12-09T18:19:26.492745400"));
        order.setPrice(BigDecimal.ONE);
        order.setCompanyId(1L);
        order.setStatus(OrderStatus.IN_PROGRESS);
        return order;
    }

    public static OrderDto createOrderDto3(){
        OrderDto order = new OrderDto();
        order.setId(3L);
        order.setUserId(2L);
        order.setDate(LocalDateTime.parse("2022-12-09T18:19:26.492745400"));
        order.setPrice(BigDecimal.ONE);
        order.setCompanyId(1L);
        order.setStatus(OrderStatus.IN_PROGRESS);
        return order;
    }
    public static OrderEntity createOrderEntity3(){
        OrderEntity order = new OrderEntity();
        order.setId(3L);
        order.setUserId(2L);
        order.setDate(LocalDateTime.parse("2022-12-09T18:19:26.492745400"));
        order.setPrice(BigDecimal.ONE);
        order.setCompanyId(1L);
        order.setStatus(OrderStatus.IN_PROGRESS);
        return order;
    }

    public static OrderDto createOrderDto4(){
        OrderDto order = new OrderDto();
        order.setId(1L);
        order.setUserId(1L);
        order.setDate(LocalDateTime.parse("2022-12-09T18:19:26.492745400"));
        order.setPrice(BigDecimal.ONE);
//        order.setCompanyId(1L);
        order.setStatus(OrderStatus.NOT_STARTED);
        return order;
    }
    public static OrderEntity createOrderEntity4(){
        OrderEntity order = new OrderEntity();
        order.setId(1L);
        order.setUserId(1L);
        order.setDate(LocalDateTime.parse("2022-12-09T18:19:26.492745400"));
        order.setPrice(BigDecimal.ONE);
//        order.setCompanyId(1L);
        order.setStatus(OrderStatus.NOT_STARTED);
        return order;
    }

}
