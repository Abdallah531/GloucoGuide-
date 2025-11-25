package com.example.GlucoGuide.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountDTO {
    private Long userId;
    private String username;
    private String password;
    private String email;
    private UserDetailsDTO details;
}