package com.example.GlucoGuide.Converter;

import com.example.GlucoGuide.DTO.RecordTypeDTO;
import com.example.GlucoGuide.Entity.RecordType;
import org.springframework.stereotype.Component;

@Component
public class RecordTypeConverter {

    public RecordTypeDTO convertToDTO(RecordType recordType) {
        return new RecordTypeDTO(
                recordType.getTypeId(),
                recordType.getType()
        );
    }

    public RecordType convertToEntity(RecordTypeDTO recordTypeDTO) {
        RecordType recordType = new RecordType();
        recordType.setTypeId(recordTypeDTO.getTypeId());
        recordType.setType(recordTypeDTO.getType());
        return recordType;
    }
}