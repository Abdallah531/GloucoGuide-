package com.example.GlucoGuide.DTO;

import com.example.GlucoGuide.Converter.AdminConverter;
import lombok.Data;

@Data
public class AdminRegisterDTO extends AdminConverter {
    private String username;
    private String password;
    private String email;
}