package com.example.GlucoGuide.Entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RecordType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long typeId;

    @Column(nullable = false)
    private String type;

    @OneToMany(mappedBy = "recordType", cascade = CascadeType.ALL)
    private List<MedicalRecord> medicalRecords;

}