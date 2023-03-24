package com.example.demo.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping(value = "/company")
public class CompanyController {
    @GetMapping(value = "/")
    String clientHome (Authentication authentication){
        String companyId = authentication.getName();
        return ("Hello, " + companyId);
    }
}
