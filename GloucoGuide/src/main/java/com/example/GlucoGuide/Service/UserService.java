package com.example.GlucoGuide.Service;

import com.example.GlucoGuide.Converter.UserConverter;
import com.example.GlucoGuide.DTO.UserAccountDTO;
import com.example.GlucoGuide.DTO.UserDetailsDTO;
import com.example.GlucoGuide.Entity.UserAccount;
import com.example.GlucoGuide.Entity.UserDetails;
import com.example.GlucoGuide.Error.AuthenticationException;
import com.example.GlucoGuide.Error.DuplicateUsernameException;
import com.example.GlucoGuide.Repository.UserAccountRepository;
import com.example.GlucoGuide.Repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private UserConverter userConverter;

    public UserAccountDTO registerUser(UserAccountDTO userAccountDTO, UserDetailsDTO userDetailsDTO) {
        // Check if the username already exists
        if (userAccountRepository.findByUsername(userAccountDTO.getUsername()) != null) {
            throw new DuplicateUsernameException("Username already exists");
        }

        // Convert UserDetailsDTO to UserDetails entity
        UserDetails userDetails = new UserDetails();
        userDetails.setFirstName(userDetailsDTO.getFirstName());
        userDetails.setLastName(userDetailsDTO.getLastName());
        userDetails.setGender(userDetailsDTO.getGender());
        userDetails.setAge(userDetailsDTO.getAge());
        userDetails.setProfilePhoto(userDetailsDTO.getProfilePhoto());
        UserDetails savedUserDetails = userDetailsRepository.save(userDetails);

        // Create UserAccount entity and set password
        UserAccount userAccount = new UserAccount();
        userAccount.setUsername(userAccountDTO.getUsername());
        userAccount.setPassword(userAccountDTO.getPassword()); // Set password directly
        userAccount.setEmail(userAccountDTO.getEmail());
        userAccount.setDetails(savedUserDetails);

        // Save user account
        UserAccount savedUserAccount = userAccountRepository.save(userAccount);

        // Convert saved UserAccount back to DTO
        UserAccountDTO registeredUserDTO = new UserAccountDTO();
        registeredUserDTO.setUserId(savedUserAccount.getUserId());
        registeredUserDTO.setUsername(savedUserAccount.getUsername());
        registeredUserDTO.setPassword(savedUserAccount.getPassword());
        registeredUserDTO.setEmail(savedUserAccount.getEmail());
        registeredUserDTO.setDetails(userDetailsDTO);

        // Set the id from saved UserDetails entity to the UserDetailsDTO
        userDetailsDTO.setId(savedUserDetails.getId());
        registeredUserDTO.setDetails(userDetailsDTO);

        return registeredUserDTO;
    }

    public UserAccountDTO updateProfilePhoto(Long id, MultipartFile photo) throws IOException {
        UserDetails userDetails = userDetailsRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        userDetails.setProfilePhoto(photo.getBytes());
        userDetailsRepository.save(userDetails);
        UserAccount userAccount =userAccountRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return userConverter.convertToDTO(userAccount);

    }

    public UserAccountDTO findByUsername(String username) {
        UserAccount userAccount = userAccountRepository.findByUsername(username);
        return userConverter.convertToDTO(userAccount);
    }

    public UserAccountDTO authenticate(String username, String password) {
        // Find user account by username
        UserAccount userAccount = userAccountRepository.findByUsername(username);
        if (userAccount != null && userAccount.getPassword().equals(password)) {
            return userConverter.convertToDTO(userAccount);
        } else {
            throw new AuthenticationException("Invalid username or password");
        }
    }

    public UserAccount getUserById(Long userId) {
        return userAccountRepository.findById(userId).orElse(null);
    }

    public List<UserAccountDTO> getAllUsers() {
        List<UserAccount> users = userAccountRepository.findAll();
        return users.stream()
                .map(userConverter::convertToDTO)
                .collect(Collectors.toList());
    }
}

