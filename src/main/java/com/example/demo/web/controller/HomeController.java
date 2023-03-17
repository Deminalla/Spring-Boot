package com.example.demo.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequiredArgsConstructor
public class HomeController {
    @GetMapping("/")
    public String homePage(){
        return ("<h1> HELLO </h1>");
    }
}