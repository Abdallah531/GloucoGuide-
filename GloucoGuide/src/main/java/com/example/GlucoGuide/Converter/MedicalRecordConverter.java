package com.example.GlucoGuide.Converter;

import com.example.GlucoGuide.DTO.MedicalRecordDTO;
import com.example.GlucoGuide.Entity.MedicalRecord;
import org.springframework.stereotype.Component;

@Component
public class MedicalRecordConverter {

    public MedicalRecordDTO convertToDTO(MedicalRecord medicalRecord) {
        return new MedicalRecordDTO(
                medicalRecord.getRecordId(),
                medicalRecord.getMeasurements(),
                medicalRecord.getNote(),
                medicalRecord.getDate(),
                medicalRecord.getUser().getUserId(),
                medicalRecord.getRecordType().getTypeId(),
                null
        );
    }
    public MedicalRecord convertToEntity(MedicalRecordDTO medicalRecordDTO) {
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setRecordId(medicalRecordDTO.getRecordId());
        medicalRecord.setMeasurements(medicalRecordDTO.getMeasurements());
        medicalRecord.setNote(medicalRecordDTO.getNote());
        medicalRecord.setDate(medicalRecordDTO.getDate());
        // The user and recordType need to be set separately
        return medicalRecord;
    }
}