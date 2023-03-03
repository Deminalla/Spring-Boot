package com.example.demo.web.controller;

import com.example.demo.business.enums.OrderStatus;
import com.example.demo.business.service.OrderService;
import com.example.demo.model.OrderDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping(value = "/order")
public class OrderController {
    private final OrderService orderService;

    @ApiOperation(value = "gets orders by the given status")
    @GetMapping(value = "/status/{status}")
    ResponseEntity<List<OrderDto>> getOrdersByStatus(@PathVariable OrderStatus status){
        log.info("Searching for orders that have a status of: {}", status);
        List<OrderDto> orderList =  orderService.findOrdersByStatus(status);
        return ResponseEntity.ok(orderList);
    }

    @ApiOperation(value = "changes order status to the given one")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The request is successful"),
            @ApiResponse(code = 404, message = "The server cannot find the requested resource")
    })
    @PutMapping(value = "/{orderId}/{status}")
    ResponseEntity<OrderDto> changeOrderStatus(@PathVariable long orderId, @PathVariable OrderStatus status){
        log.info("Change status for order {} to: {}", orderId, status);
        Optional<OrderDto> order = orderService.findOrderById(orderId);
        if(order.isEmpty()){
            log.warn("Order with id {} not found", orderId);
            return ResponseEntity.notFound().build();
        }
        OrderDto orderChangedStatus = orderService.changeStatus(order.get(), status);
        log.info("Order with id {} now has status of {}", orderId, status);
        return ResponseEntity.ok(orderChangedStatus);
    }

    // post
    // create new order
    @PostMapping(value = "/new")
    ResponseEntity<OrderDto> createOrder(Long userId, String userPassword, BigDecimal orderPrice){
        // validate whether that user exists
        // whether id and password match
        // whether user balance is enough

        // by default create post that is NOT_STARTED

        return null;
    }
}
