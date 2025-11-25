package com.example.GlucoGuide.Converter;

import com.example.GlucoGuide.DTO.AdminDTO;

import com.example.GlucoGuide.DTO.AdminRegisterDTO;
import com.example.GlucoGuide.Entity.Admin;
import org.springframework.stereotype.Component;

@Component
public class AdminConverter {

    public AdminDTO convertToDTO(Admin admin) {
        AdminDTO dto = new AdminDTO();
        dto.setId(admin.getId());
        dto.setUsername(admin.getUsername());
        dto.setPassword(admin.getPassword());
        dto.setEmail(admin.getEmail());
        return dto;
    }

    public Admin convertToEntity(AdminDTO dto) {
        Admin admin = new Admin();
        admin.setId(dto.getId());
        admin.setUsername(dto.getUsername());
        admin.setPassword(dto.getPassword());
        admin.setEmail(dto.getEmail());
        return admin;
    }

    public Admin convertToEntity(AdminRegisterDTO dto) {
        Admin admin = new Admin();
        admin.setUsername(dto.getUsername());
        admin.setPassword(dto.getPassword());
        admin.setEmail(dto.getEmail());
        return admin;
    }


}