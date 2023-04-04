package com.example.demo.model;

import lombok.Data;

@Data
public class JwtResponseModel {
    private final String token;

    public JwtResponseModel(String token) {
        this.token = token;
    }
}
