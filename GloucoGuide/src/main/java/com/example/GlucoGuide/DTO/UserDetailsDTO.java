package com.example.GlucoGuide.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String gender;
    private Integer age;
    private byte[] profilePhoto; // Add profile photo to DTO

}