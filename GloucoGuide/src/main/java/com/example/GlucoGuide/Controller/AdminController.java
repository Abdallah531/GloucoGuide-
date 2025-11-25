package com.example.GlucoGuide.Controller;

import com.example.GlucoGuide.DTO.AdminDTO;
import com.example.GlucoGuide.DTO.AdminRegisterDTO;
import com.example.GlucoGuide.Error.DuplicateUsernameException;
import com.example.GlucoGuide.Service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private HttpServletRequest request;

    private static final String SESSION_ATTR_ADMIN = "authenticatedAdmin";

    private void setAuthenticatedAdmin(AdminDTO adminDTO) {
        HttpSession session = request.getSession(true);
        session.setAttribute(SESSION_ATTR_ADMIN, adminDTO);
    }

    private AdminDTO getAuthenticatedAdmin() {
        HttpSession session = request.getSession(false);
        if (session != null) {
            return (AdminDTO) session.getAttribute(SESSION_ATTR_ADMIN);
        }
        return null;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody AdminRegisterDTO adminRegisterDTO) {
        try {
            //adminService.registerAdmin(adminRegisterDTO);
            AdminDTO adminDTO = adminService.registerAdmin(adminRegisterDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(adminDTO);
        } catch (DuplicateUsernameException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        }
    }


    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestParam("username") String username, @RequestParam("password") String password) {
        try {
            AdminDTO adminDTO = adminService.authenticate(username, password);
            setAuthenticatedAdmin(adminDTO);
            return ResponseEntity.ok(adminDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sign in failed: " + e.getMessage());
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<?> profilePage() {
        AdminDTO adminDTO = getAuthenticatedAdmin();
        if (adminDTO == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not authenticated");
        }
        return ResponseEntity.ok(adminDTO);
    }

//    @PutMapping("/update")
//    public ResponseEntity<String> updateProfile(@RequestParam("newUsername") String newUsername, @RequestParam("newPassword") String newPassword) {
//        AdminDTO adminDTO = getAuthenticatedAdmin();
//        if (adminDTO != null) {
//            try {
//                adminService.updateAdminProfile(adminDTO.getId(), newUsername, newPassword);
//                return ResponseEntity.ok("Profile updated successfully");
//            } catch (Exception e) {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Update failed: " + e.getMessage());
//            }
//        }
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not authenticated");
//    }
    @PutMapping("/update")
    public ResponseEntity<AdminDTO> updateProfile(@RequestParam("newUsername") String newUsername, @RequestParam("newPassword") String newPassword) {
        AdminDTO adminDTO = getAuthenticatedAdmin();
        if (adminDTO != null) {
            try {
                AdminDTO updatedAdminDTO = adminService.updateAdminProfile(adminDTO.getId(), newUsername, newPassword);
                return ResponseEntity.ok(updatedAdminDTO);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteAccount() {
        AdminDTO adminDTO = getAuthenticatedAdmin();
        if (adminDTO != null) {
            adminService.deleteAdmin(adminDTO.getId());
            request.getSession().invalidate();
            return ResponseEntity.ok("Account deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not authenticated");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        request.getSession().invalidate();
        return ResponseEntity.ok("Logged out successfully");
    }
}
