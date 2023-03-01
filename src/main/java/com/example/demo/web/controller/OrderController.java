package com.example.demo.web.controller;

import com.example.demo.business.enums.OrderStatus;
import com.example.demo.business.service.OrderService;
import com.example.demo.model.OrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping(value = "/order")
public class OrderController {
    private final OrderService orderService;

    @GetMapping(value = "/paid/{paid}")
    ResponseEntity<List<OrderDto>> getOrdersByPaidOrNot(@PathVariable boolean paid){
        log.info("Searching for orders that been paid: {}", paid);
        List<OrderDto> orderList =  orderService.findOrdersByPaidOrNot(paid);
        return ResponseEntity.ok(orderList);
    }

    @PutMapping(value = "/{orderNr}/{status}")
    ResponseEntity<OrderDto> changeOrderStatus(@PathVariable long orderNr, @PathVariable OrderStatus status){
        log.info("Change status for order {} to: {}", orderNr, status);
        Optional<OrderDto> order = orderService.findOrderByNr(orderNr);
        if(order.isEmpty()){
            log.warn("Order with number {} not found", orderNr);
            return ResponseEntity.notFound().build();
        }
        OrderDto orderChangedStatus = orderService.changeStatus(order.get(), status);
        log.info("Order with number {} now has status of {}", orderNr, status);
        return ResponseEntity.ok(orderChangedStatus);
    }

    // post
    // create new order

}
