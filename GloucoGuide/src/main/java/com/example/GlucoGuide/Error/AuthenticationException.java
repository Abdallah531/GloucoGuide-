package com.example.GlucoGuide.Error;


public class AuthenticationException extends RuntimeException {
    public AuthenticationException(String message) {
        super(message);
    }
}