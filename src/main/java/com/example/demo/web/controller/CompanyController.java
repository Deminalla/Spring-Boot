package com.example.demo.web.controller;

import com.example.demo.security.IAuthenticationFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping(value = "/company")
public class CompanyController {
    private final IAuthenticationFacade authentication;

    @GetMapping(value = "/")
    String clientHome (){
        String companyId = authentication.getAuthentication().getName();
        return ("Hello, " + companyId);
    }
}
