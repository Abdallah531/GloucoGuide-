package com.example.GlucoGuide.Converter;

import com.example.GlucoGuide.DTO.UserAccountDTO;
import com.example.GlucoGuide.DTO.UserDetailsDTO;
import com.example.GlucoGuide.Entity.UserAccount;
import com.example.GlucoGuide.Entity.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    public UserAccountDTO convertToDTO(UserAccount userAccount) {
        UserDetails details = userAccount.getDetails();
        UserDetailsDTO detailsDTO = new UserDetailsDTO(
                details.getId(),
                details.getFirstName(),
                details.getLastName(),
                details.getGender(),
                details.getAge(),
                details.getProfilePhoto() // Include profile photo in the DTO
        );
        return new UserAccountDTO(
                userAccount.getUserId(),
                userAccount.getUsername(),
                userAccount.getPassword(),
                userAccount.getEmail(),
                detailsDTO
        );
    }

    public UserAccount convertToEntity(UserAccountDTO userAccountDTO) {
        UserDetailsDTO detailsDTO = userAccountDTO.getDetails();
        UserDetails details = new UserDetails(
                detailsDTO.getId(),
                detailsDTO.getFirstName(),
                detailsDTO.getLastName(),
                detailsDTO.getGender(),
                detailsDTO.getAge(),
                detailsDTO.getProfilePhoto(), // Include profile photo in the entity
                null
        );
        return new UserAccount(
                userAccountDTO.getUserId(),
                userAccountDTO.getUsername(),
                userAccountDTO.getPassword(),
                userAccountDTO.getEmail(),
                details,
                null,
                null,
                null,
                null
        );
    }
}
