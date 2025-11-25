package com.example.GlucoGuide.Controller;


import com.example.GlucoGuide.DTO.UserAccountDTO;
import com.example.GlucoGuide.DTO.UserDetailsDTO;
import com.example.GlucoGuide.DTO.UserSignInRequest;
import com.example.GlucoGuide.DTO.UserSignupRequest;
import com.example.GlucoGuide.Error.DuplicateUsernameException;
import com.example.GlucoGuide.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/user")
@SessionAttributes("userAccount") // This annotation ensures that the userAccount object is stored in the session
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest request;

    private static final String SESSION_ATTR_USER = "authenticatedUser";

    private void setAuthenticatedUser(UserAccountDTO userAccountDTO) {
        HttpSession session = request.getSession(true);

        session.setAttribute(SESSION_ATTR_USER, userAccountDTO);
    }

    private UserAccountDTO getAuthenticatedUser() {
        HttpSession session = request.getSession(false);
        if (session != null) {
            return (UserAccountDTO) session.getAttribute(SESSION_ATTR_USER);
        }
        return null;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> processSignup(@RequestBody UserAccountDTO userAccountDTO) {

        UserDetailsDTO userDetailsDTO = userAccountDTO.getDetails();

        try {
            UserAccountDTO registeredUser = userService.registerUser(userAccountDTO, userDetailsDTO);
            setAuthenticatedUser(registeredUser);
            UserAccountDTO authenticatedUser = getAuthenticatedUser();
            return ResponseEntity.status(HttpStatus.CREATED).body(authenticatedUser);
        } catch (DuplicateUsernameException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        }
    }

    @PostMapping("/upload-photo")
    public ResponseEntity<?> uploadProfilePhoto(@RequestParam("photo") MultipartFile photo) throws IOException {
        UserAccountDTO authenticatedUser = getAuthenticatedUser();
        if (authenticatedUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not authenticated");
        }
        try {
        Long userID = authenticatedUser.getUserId();
        userService.updateProfilePhoto(userID, photo);
        UserAccountDTO authenticatedUser1 = getAuthenticatedUser();
        return ResponseEntity.ok(authenticatedUser1);
        } catch (Exception e){
            return ResponseEntity.ok("Photo uploaded failed");
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> processSignIn(@RequestBody UserSignInRequest userSignInRequest) {
        String username = userSignInRequest.getUsername();
        String password = userSignInRequest.getPassword();
        try {
            UserAccountDTO userAccountDTO = userService.authenticate(username, password);
            setAuthenticatedUser(userAccountDTO);
            UserAccountDTO authenticatedUser = getAuthenticatedUser();
            return ResponseEntity.ok(authenticatedUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed: " + e.getMessage());
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<?> showProfile() {
        UserAccountDTO authenticatedUser = getAuthenticatedUser();
        if (authenticatedUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not authenticated");
        }
        return ResponseEntity.ok(authenticatedUser);
    }

    @PostMapping("/signout")
    public ResponseEntity<String> signOut() {
        request.getSession().invalidate();
        return ResponseEntity.ok("User signed out successfully");
    }
}


