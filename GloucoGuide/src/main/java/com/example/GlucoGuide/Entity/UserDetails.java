package com.example.GlucoGuide.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private Integer age;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "profile_photo",columnDefinition = "MEDIUMBLOB" )
    private byte[] profilePhoto;

    @OneToOne(mappedBy = "details", cascade = CascadeType.ALL)
    @JsonBackReference
    private UserAccount userAccount;

}
