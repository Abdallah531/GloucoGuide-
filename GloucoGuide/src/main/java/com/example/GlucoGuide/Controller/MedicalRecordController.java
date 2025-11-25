package com.example.GlucoGuide.Controller;

import com.example.GlucoGuide.Converter.UserConverter;
import com.example.GlucoGuide.DTO.CategoryDTO;
import com.example.GlucoGuide.DTO.MedicalRecordDTO;
import com.example.GlucoGuide.DTO.RecordTypeDTO;
import com.example.GlucoGuide.DTO.UserAccountDTO;
import com.example.GlucoGuide.Entity.RecordType;
import com.example.GlucoGuide.Entity.UserAccount;
import com.example.GlucoGuide.Service.RecordService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/medical-records")
public class MedicalRecordController {

    @Autowired
    private RecordService recordService;

    @Autowired
    private UserConverter userConverter;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadMedicalRecord(@RequestBody MedicalRecordDTO medicalRecordDTO, HttpSession session) {
        UserAccountDTO userDTO = (UserAccountDTO) session.getAttribute("authenticatedUser");
        if (userDTO == null) {
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
        }
        Long userId = userDTO.getUserId();
        medicalRecordDTO.setUserId(userId);
        MedicalRecordDTO createdRecord = recordService.saveMedicalRecord(medicalRecordDTO);
        return new ResponseEntity<>(createdRecord, HttpStatus.CREATED);
    }

    @GetMapping("/types")
    public ResponseEntity<List<RecordTypeDTO>> getAllCategories() {
        List<RecordTypeDTO> RecordTypes = recordService.getAllRecordTypes();
        return ResponseEntity.ok(RecordTypes);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getMedicalRecords(HttpSession session) {
        UserAccountDTO userDTO = (UserAccountDTO) session.getAttribute("authenticatedUser");
        if (userDTO == null) {
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
        }
        Long userId = userDTO.getUserId();
        List<MedicalRecordDTO> medicalRecords = recordService.getAllMedicalRecordsForUser(userId);
        return new ResponseEntity<>(medicalRecords, HttpStatus.OK);
    }

    @GetMapping("/list/{recordTypeId}")
    public ResponseEntity<?> getMedicalRecordsByType(@PathVariable Long recordTypeId, HttpSession session) {
        UserAccountDTO userDTO = (UserAccountDTO) session.getAttribute("authenticatedUser");
        if (userDTO == null) {
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
        }
        Long userId = userDTO.getUserId();
        List<MedicalRecordDTO> medicalRecords = recordService.getAllMedicalRecordsForUserByRecordType(userId, recordTypeId);
        return new ResponseEntity<>(medicalRecords, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateMedicalRecord(@RequestBody MedicalRecordDTO medicalRecordDTO, HttpSession session) {
        UserAccountDTO userDTO = (UserAccountDTO) session.getAttribute("authenticatedUser");
        if (userDTO == null) {
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
        }

        try {
            MedicalRecordDTO updatedRecord = recordService.updateMedicalRecord(medicalRecordDTO);
            return new ResponseEntity<>(updatedRecord, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update medical record: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMedicalRecord(@PathVariable Long id, HttpSession session) {
        UserAccountDTO userDTO = (UserAccountDTO) session.getAttribute("authenticatedUser");
        if (userDTO == null) {
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
        }

        recordService.deleteMedicalRecord(id);
        return new ResponseEntity<>("Medical record deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/graph")
    public ResponseEntity<?> getMedicalRecordsForGraph(
            @RequestParam Long recordTypeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            HttpSession session) {

        UserAccountDTO userDTO = (UserAccountDTO) session.getAttribute("authenticatedUser");
        if (userDTO == null) {
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
        }
        UserAccount user = userConverter.convertToEntity(userDTO);
        RecordType recordType = recordService.getRecordTypeById(recordTypeId);
        Date endDate = new Date(); // Current date
        List<MedicalRecordDTO> medicalRecords = recordService.getMedicalRecordsByTypeAndDateRange(user, recordType, startDate, endDate);

        return new ResponseEntity<>(medicalRecords, HttpStatus.OK);
    }
}

