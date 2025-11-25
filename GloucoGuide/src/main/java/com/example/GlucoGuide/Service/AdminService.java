package com.example.GlucoGuide.Service;

import com.example.GlucoGuide.DTO.AdminDTO;
import com.example.GlucoGuide.DTO.AdminRegisterDTO;
import com.example.GlucoGuide.Converter.AdminConverter;
import com.example.GlucoGuide.Entity.Admin;
import com.example.GlucoGuide.Error.AuthenticationException;
import com.example.GlucoGuide.Error.DuplicateUsernameException;
import com.example.GlucoGuide.Repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

//@Service
//public class AdminService {
//
//    @Autowired
//    private AdminRepository adminRepository;
//
//    @Autowired
//    private AdminConverter adminConverter;
//
//    public void registerAdmin(AdminRegisterDTO adminRegisterDTO) {
//        Admin admin = adminConverter.convertToEntity(adminRegisterDTO);
//        try {
//            adminRepository.save(admin);
//        } catch (DataIntegrityViolationException e) {
//            throw new DuplicateUsernameException("Username already exists");
//        }
//    }
//
//    public AdminDTO findByUsername(String username) {
//        Admin admin = adminRepository.findByUsername(username);
//        return admin != null ? adminConverter.convertToDTO(admin) : null;
//    }
//
//    public Admin getAdminById(Long adminId) {
//        return adminRepository.findById(adminId).orElse(null);
//    }
//
//    public AdminDTO authenticate(String username, String password) throws AuthenticationException {
//        Admin admin = adminRepository.findByUsername(username);
//        if (admin != null && admin.getPassword().equals(password)) {
//            return adminConverter.convertToDTO(admin);
//        } else {
//            throw new AuthenticationException("Invalid username or password");
//        }
//    }
//
//    public void updateAdminProfile(Long adminId, String newUsername, String newPassword) throws DuplicateUsernameException {
//        Admin admin = adminRepository.findById(adminId).orElse(null);
//        if (admin != null) {
//            admin.setUsername(newUsername);
//            admin.setPassword(newPassword);
//            try {
//                adminRepository.save(admin);
//            } catch (DataIntegrityViolationException e) {
//                throw new DuplicateUsernameException("Username already exists");
//            }
//        }
//    }
//
//    public void deleteAdmin(Long adminId) {
//        adminRepository.deleteById(adminId);
//    }
//}


@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AdminConverter adminConverter;

    public AdminDTO registerAdmin(AdminRegisterDTO adminRegisterDTO) {
        Admin admin = adminConverter.convertToEntity(adminRegisterDTO);
        try {
            Admin savedAdmin = adminRepository.save(admin);
            return adminConverter.convertToDTO(savedAdmin);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateUsernameException("Username already exists");
        }
    }

    public AdminDTO findByUsername(String username) {
        Admin admin = adminRepository.findByUsername(username);
        return admin != null ? adminConverter.convertToDTO(admin) : null;
    }

    public AdminDTO authenticate(String username, String password) throws AuthenticationException {
        Admin admin = adminRepository.findByUsername(username);
        if (admin != null && admin.getPassword().equals(password)) {
            return adminConverter.convertToDTO(admin);
        } else {
            throw new AuthenticationException("Invalid username or password");
        }
    }

    public AdminDTO updateAdminProfile(Long adminId, String newUsername, String newPassword) throws DuplicateUsernameException {
        Admin admin = adminRepository.findById(adminId).orElse(null);
        if (admin != null) {
            admin.setUsername(newUsername);
            admin.setPassword(newPassword); // Consider hashing the password here
            try {
                Admin updatedAdmin = adminRepository.save(admin);
                return adminConverter.convertToDTO(updatedAdmin);
            } catch (DataIntegrityViolationException e) {
                throw new DuplicateUsernameException("Username already exists");
            }
        }
        return null; // or throw exception if adminId is not found
    }
    public Admin getAdminById(Long adminId) {
        return adminRepository.findById(adminId).orElse(null);
    }

    public void deleteAdmin(Long adminId) {
        adminRepository.deleteById(adminId);
    }
}
