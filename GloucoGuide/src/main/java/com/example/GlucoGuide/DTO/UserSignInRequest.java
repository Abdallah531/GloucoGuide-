package com.example.GlucoGuide.DTO;

import lombok.Data;

@Data
public class UserSignInRequest {
    private String username;
    private String password;
}
