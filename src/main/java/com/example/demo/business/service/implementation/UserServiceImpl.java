package com.example.demo.business.service.implementation;

import com.example.demo.business.mapper.UserEntityMapStruct;
import com.example.demo.business.repository.UserRepository;
import com.example.demo.business.repository.model.UserEntity;
import com.example.demo.business.service.UserService;
import com.example.demo.model.NewUserDto;
import com.example.demo.model.UserDto;
import com.example.demo.model2.ClientDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
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
    public BigDecimal modifyBalance(String username, BigDecimal money, boolean add) {
        Optional<UserDto> userOptional = findUserByUsername(username);
        UserDto user = userOptional.get();

        if(money.compareTo(BigDecimal.ZERO)<=0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Amount should be a positive number");
        }

        if(add){
            log.info("Adding {} to user {} balance", money, user.getUsername());
            BigDecimal newBalance = user.getBalance().add(money);
            user.setBalance(newBalance);
        }
        else{
            enoughBalance(user, money);
            log.info("Extracting {} from user {} balance", money, user.getUsername());
            BigDecimal newBalance = user.getBalance().subtract(money);
            user.setBalance(newBalance);
        }
        UserEntity userEntity = userMapper.dtoToEntity(user);
        userRepository.save(userEntity);
        return user.getBalance();
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

    @Override
    public UserDto createNewUser(NewUserDto newUserDto) {
        log.info("Creating new user");
        Optional<UserDto> userDto = findUserByUsername(newUserDto.getUsername());
        if(userDto.isPresent()){
            log.warn("User with username {} already exists", newUserDto.getUsername());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username is already taken, enter a different username");
        }

        Random rand = new Random();
        int rand_int = rand.nextInt(10000); // Generate random integers in range 0 to 9999

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(newUserDto.getUsername());
        userEntity.setEmail(newUserDto.getEmail());
        userEntity.setPassword(newUserDto.getPassword());
        userEntity.setBalance(BigDecimal.ZERO);
        userEntity.setCode(rand_int);
        userRepository.save(userEntity);

        return userMapper.entityToDto(userEntity);
    }

}
