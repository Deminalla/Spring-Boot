package com.example.demo.business.service.implementation;

import com.example.demo.business.service.UserService;
import com.example.demo.model2.ClientDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService {
    private final RestTemplate restTemplate; // instead of RestTemplate restTemplate = new RestTemplate(); all the time

    @Override
    public ClientDto getClientName(long id) {
        log.info("Connecting to microservice2");

        // first argument - the url we want to call
        // gets back a json, which can it turns into object for the class we gave
        ClientDto client = restTemplate.getForObject("http://localhost:9091/client/" + id, ClientDto.class);

        // Retrieve an entity by doing a GET on the specified URL. The response is converted and stored in an ResponseEntity.
        ResponseEntity<ClientDto> responseEntity = restTemplate.getForEntity("http://localhost:9091/client/" + id, ClientDto.class);
        ClientDto clientBody = responseEntity.getBody();

        return client;
    }
}
