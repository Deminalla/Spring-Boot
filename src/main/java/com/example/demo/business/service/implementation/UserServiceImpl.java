package com.example.demo.business.service.implementation;

import com.example.demo.business.mapper.UserEntityMapStruct;
import com.example.demo.business.repository.UserRepository;
import com.example.demo.business.repository.model.UserEntity;
import com.example.demo.business.service.UserService;
import com.example.demo.model.UserDto;
import com.example.demo.model2.ClientDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService {
    private final RestTemplate restTemplate; // instead of RestTemplate restTemplate = new RestTemplate(); all the time
    private final UserRepository userRepository;
    private final UserEntityMapStruct userMapper;

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

    @Override
    public UserDto extractMoney(UserDto user, BigDecimal money) {
        enoughBalance(user, money);

        log.info("Extracting money from user {} balance", user.getUsername());
        BigDecimal newBalance = user.getBalance().subtract(money);
        user.setBalance(newBalance);
        UserEntity userEntity = userMapper.dtoToEntity(user);
        userRepository.save(userEntity);
        return user;
    }

    @Override
    public Optional<UserDto> findUserByUsername(String username) {
        log.info("Searching for user {}", username);
        Optional<UserEntity> userByName = userRepository.findByUsername(username);
        Optional<UserDto> userDtoByName = userByName.flatMap(user -> Optional.ofNullable(userMapper.entityToDto(user)));
        log.info("User with name {} is {}", username, userDtoByName);

        return userDtoByName;
    }

    @Override
    public void checkConfirmationCode(UserDto user, int code) {
        log.info("Checking whether user {} has confirmation code of {}", user.getUsername(), code);
        if(!user.getCode().equals(code)){
            log.warn("User {} does not contain the code {}", user.getUsername(), code);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User has provided the wrong code");
        }
    }

    @Override
    public boolean enoughBalance(UserDto user, BigDecimal price) {
        log.info("Checking whether user {} has enough balance", user.getUsername());
        if(user.getBalance().compareTo(price)<0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User does not have enough fund to make an order");
        }
        return true;

    }

}
