package com.example.demo.web.controller;

import com.example.demo.business.service.UserService;
import com.example.demo.model.NewUserDto;
import com.example.demo.model.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequiredArgsConstructor
public class HomeController {
    private final UserService userService;

    @GetMapping("/")
    public String homePage(){
        return ("<h1> HELLO </h1>");
    }

    @PostMapping(value = "/new_user")
    ResponseEntity<UserDto> createUser (@RequestBody NewUserDto newUserDto){
        UserDto userDto = userService.createNewUser(newUserDto);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);

    }
}