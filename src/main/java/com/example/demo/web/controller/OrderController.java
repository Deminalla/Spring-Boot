package com.example.demo.web.controller;

import com.example.demo.business.enums.OrderStatus;
import com.example.demo.business.service.OrderService;
import com.example.demo.business.service.UserService;
import com.example.demo.model.OrderDto;
import com.example.demo.model.UserDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
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
    private final UserService userService;

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


    @PostMapping(value = "/new_order")
    ResponseEntity<OrderDto> createOrder(String username, int code, BigDecimal price){
        Optional<UserDto> user = userService.findUserByUsername(username);
        if(user.isEmpty()){
            log.warn("User {} not found", username);
            return ResponseEntity.notFound().build();
        }

        userService.checkConfirmationCode(user.get(), code);

        orderService.createOrder(user.get(), price);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

}
