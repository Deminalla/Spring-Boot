package com.example.demo.helper;

import com.fasterxml.jackson.databind.ObjectMapper;

public class HelperClass {
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
