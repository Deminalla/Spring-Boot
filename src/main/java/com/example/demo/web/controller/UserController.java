package com.example.demo.web.controller;

import com.example.demo.business.service.UserService;
import com.example.demo.model2.ClientDto;
import com.example.demo.security.IAuthenticationFacade;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping(value = "/user")
public class UserController {
    private final UserService userService;
    private final IAuthenticationFacade authentication;

    @GetMapping("/")
    public String userHomePage(){
        String username = authentication.getAuthentication().getName();
        return ("Hello, " + username);
    }

    @GetMapping(value = "/{id}")
    ResponseEntity<ClientDto> getClientNameSurname(@PathVariable long id){
        log.info("Looking for name and surname of client with id {}", id);
        ClientDto client =  userService.getClientName(id);
        return ResponseEntity.ok(client);
    }

    @ApiOperation(value = "Adds money to user's balance")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The request is successful"),
            @ApiResponse(code = 400, message = "The request cannot be processed")
    })
    @PutMapping(value = "add_money")
    ResponseEntity<?> addMoneyToBalance(BigDecimal money){
        log.info("Adding money to balance");

        userService.modifyBalance(authentication.getAuthentication().getName(), money, true);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
