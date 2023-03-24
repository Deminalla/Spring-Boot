package com.example.demo.web.controller;

import com.example.demo.business.service.UserService;
import com.example.demo.model.NewUserDto;
import com.example.demo.model2.ClientDto;
import com.example.demo.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping(value = "/user")
public class UserController {
    private final UserService userService;
    private final AuthenticationFacade authentication;

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

    // should probably move this or change auth, because right now, this can be accessed by logged in users
    // which makes no sense
    @PostMapping(value = "/new_user")
    ResponseEntity<?> createUser (@RequestBody NewUserDto newUserDto){
        userService.createNewUser(newUserDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
