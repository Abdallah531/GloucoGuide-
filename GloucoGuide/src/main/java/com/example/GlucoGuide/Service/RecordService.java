// RecordService.java
package com.example.GlucoGuide.Service;

import com.example.GlucoGuide.Converter.MedicalRecordConverter;
import com.example.GlucoGuide.Converter.RecordTypeConverter;
import com.example.GlucoGuide.DTO.CategoryDTO;
import com.example.GlucoGuide.DTO.MedicalRecordDTO;
import com.example.GlucoGuide.DTO.RecordTypeDTO;
import com.example.GlucoGuide.Entity.MedicalRecord;
import com.example.GlucoGuide.Entity.RecordType;
import com.example.GlucoGuide.Entity.UserAccount;
import com.example.GlucoGuide.Repository.MedicalRecordRepository;
import com.example.GlucoGuide.Repository.RecordTypeRepository;
import com.example.GlucoGuide.Repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecordService {

    @Autowired
    private RecordTypeRepository recordTypeRepository;

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private UserAccountRepository userRepository;

    @Autowired
    private MedicalRecordConverter medicalRecordConverter;

    @Autowired
    private RecordTypeConverter recordTypeConverter;

    public List<MedicalRecordDTO> getAllMedicalRecordsForUser(Long userId) {
        List<MedicalRecord> medicalRecords = medicalRecordRepository.findByUserUserId(userId);
        return medicalRecords.stream()
                .map(medicalRecordConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<MedicalRecordDTO> getAllMedicalRecordsForUserByRecordType(Long userId, Long typeID ) {
        List<MedicalRecord> medicalRecords = medicalRecordRepository.findByUserUserIdAndRecordTypeTypeId(userId,typeID );
        return medicalRecords.stream()
                .map(medicalRecordConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    public MedicalRecordDTO getMedicalRecordById(Long id) {
        MedicalRecord medicalRecord = medicalRecordRepository.findById(id).orElse(null);
        return medicalRecord != null ? medicalRecordConverter.convertToDTO(medicalRecord) : null;
    }

    public List<RecordTypeDTO> getAllRecordTypes() {
        return recordTypeRepository.findAll().stream()
                .map(recordTypeConverter::convertToDTO)
                .collect(Collectors.toList());
    }
    public MedicalRecordDTO saveMedicalRecord(MedicalRecordDTO medicalRecordDTO) {

        MedicalRecord medicalRecord = medicalRecordConverter.convertToEntity(medicalRecordDTO);
        medicalRecord.setUser(userRepository.findById(medicalRecordDTO.getUserId()).orElse(null));
        medicalRecord.setRecordType(recordTypeRepository.findById(medicalRecordDTO.getRecordTypeId()).orElse(null));
        medicalRecord.setDate(new Date());
        MedicalRecord savedRecord = medicalRecordRepository.save(medicalRecord);
        MedicalRecordDTO savedRecordDTO = medicalRecordConverter.convertToDTO(savedRecord);

        if (medicalRecord.getRecordType().getTypeId() == 1) {
            String advice = generateBloodGlucoseAdvice(medicalRecord);
            savedRecordDTO.setAdvice(advice);
        }

        return savedRecordDTO;
    }


    public String generateBloodGlucoseAdvice(MedicalRecord medicalRecord) {
        RecordType recordType = medicalRecord.getRecordType();

        if (recordType.getTypeId() != 1) {
            return "Advice not applicable for this record type.";
        }
        // For example:
        double glucoseLevel = medicalRecord.getMeasurements();
        if (glucoseLevel < 70) {
            return "Your blood glucose level is low. Consume fast-acting carbohydrates to raise blood sugar quickly. If experiencing symptoms like shakiness, sweating, or confusion, seek medical attention.";
        } else if (glucoseLevel >= 70 && glucoseLevel < 100) {
            return "Your blood glucose level is within the normal range. If you're diabetic, consult your doctor to see if this is a normal range for you and if any adjustments to medication are needed.";
        } else if (glucoseLevel >= 100 && glucoseLevel < 126) {
            return "Your blood glucose level indicates prediabetes. Lifestyle changes are crucial to prevent progression. Consider diet modifications, increased physical activity, and regular blood sugar monitoring.";
        } else if (glucoseLevel >= 126 && glucoseLevel < 200) {
            return "Your blood glucose level is high, especially after fasting. Monitor blood sugar more frequently and contact your doctor to discuss possible medication adjustments.";
        } else if (glucoseLevel >= 200 && glucoseLevel < 300) {
            return "Your blood glucose level is very high, especially after a meal. Contact your doctor immediately to discuss medication adjustments.";
        } else {
            return "Your blood glucose level is dangerously high. Seek immediate medical attention. Do not wait.";
        }
    }

    public MedicalRecordDTO updateMedicalRecord(MedicalRecordDTO medicalRecordDTO) {
        Optional<MedicalRecord> optionalRecord = medicalRecordRepository.findById(medicalRecordDTO.getRecordId());

        if (optionalRecord.isPresent()) {
            MedicalRecord existingRecord = optionalRecord.get();
            existingRecord.setMeasurements(medicalRecordDTO.getMeasurements());
            existingRecord.setDate(medicalRecordDTO.getDate());
            existingRecord.setNote(medicalRecordDTO.getNote());
            existingRecord.setRecordType(getRecordTypeById(medicalRecordDTO.getRecordTypeId()));
            MedicalRecord updatedRecord = medicalRecordRepository.save(existingRecord);
            return medicalRecordConverter.convertToDTO(updatedRecord);
        } else {
            throw new RuntimeException("Medical record not found with ID: " + medicalRecordDTO.getRecordId());
        }
    }
    public RecordType getRecordTypeById(Long id) {
        return recordTypeRepository.findById(id).orElse(null);
    }

    public void deleteMedicalRecord(Long id) {
       medicalRecordRepository.deleteById(id);
    }

    public List<MedicalRecordDTO> getMedicalRecordsByTypeAndDateRange(UserAccount user, RecordType recordType, Date startDate, Date endDate) {
        List<MedicalRecord> records = medicalRecordRepository.findByUserAndRecordTypeAndDateBetweenOrderByDateAsc(user, recordType, startDate, endDate);
        return records.stream()
                .map(medicalRecordConverter::convertToDTO)
                .collect(Collectors.toList());
    }
}
