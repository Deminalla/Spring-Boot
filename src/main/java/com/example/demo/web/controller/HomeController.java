package com.example.demo.web.controller;

import com.example.demo.business.service.UserService;
import com.example.demo.model.JwtRequestModel;
import com.example.demo.model.JwtResponseModel;
import com.example.demo.model.NewUserDto;
import com.example.demo.model.UserDto;
import com.example.demo.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequiredArgsConstructor
public class HomeController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    @GetMapping("/")
    public String homePage(){
        return ("<h1> HELLO </h1>");
    }

    @PostMapping(value = "/new_user")
    ResponseEntity<UserDto> createUser (@RequestBody NewUserDto newUserDto){
        UserDto userDto = userService.createNewUser(newUserDto);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);

    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequestModel jwtRequest) throws Exception {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
        } catch (BadCredentialsException e){
            throw new Exception("Incorrect username or password", e);
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getUsername());
        String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponseModel(jwt));
    }

}