package com.example.demo.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final UserDetailsService userDetailsService;
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder())
                .and().build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/user/**").hasRole("USER")
                .antMatchers("/company/**").hasRole("COMPANY")
                .antMatchers("/order/**").hasAnyRole("USER", "COMPANY")
                .antMatchers("/**").anonymous() // no role
                .antMatchers("authenticate").permitAll()
                .anyRequest().authenticated()
                .and().httpBasic() // for basic authorization with postman
                .and().formLogin();
        http.csrf().disable(); // for put/post method authorization on postman
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() { // password encoding instead of plaintext
        return new BCryptPasswordEncoder();
    }
}
