package com.example.demo.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService); // passing userDetails instance, to get user info
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception { // authorization setup
        http.authorizeRequests()
                .antMatchers("/user/**").hasRole("USER")
                .antMatchers("/company/**").hasRole("COMPANY")
                .antMatchers("/order/**").hasAnyRole("USER", "COMPANY")
                .antMatchers("/**").hasAnyRole("USER", "COMPANY")
                .and().formLogin();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() { // password encoding instead of plaintext
        return new BCryptPasswordEncoder();
    }
}
