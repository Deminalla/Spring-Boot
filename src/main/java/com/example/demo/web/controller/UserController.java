package com.example.demo.web.controller;

import com.example.demo.model2.ClientDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping(value = "/user")
public class UserController {

    @GetMapping(value = "/{id}")
    ResponseEntity<ClientDto> getClientNameSurname(@PathVariable long id){
        RestTemplate restTemplate = new RestTemplate();
        // first argument - the url we want to call
        // gets back a json, which can it turns into object for the class we gave
        ClientDto client = restTemplate.getForObject("http://localhost:9091/client/" + id, ClientDto.class);

        // this is if the return of the microservice is ResponseEntity
        ResponseEntity<ClientDto> entity = restTemplate.getForEntity("http://localhost:9091/client/" + id, ClientDto.class);

        return ResponseEntity.ok(entity.getBody());
    }
}
