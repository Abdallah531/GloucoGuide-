package com.example.GlucoGuide;

import com.example.GlucoGuide.Entity.Category;
import com.example.GlucoGuide.Entity.RecordType;
import com.example.GlucoGuide.Repository.CategoryRepository;
import com.example.GlucoGuide.Repository.RecordTypeRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    @Autowired
    private RecordTypeRepository recordTypeRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @PostConstruct
    public void init() {
        // Predefine the record types
        createRecordTypeIfNotExists("Glucose Measures");
        createRecordTypeIfNotExists("Insulin Levels");
        createRecordTypeIfNotExists("Carbohydrate");
        createCategoryIfNotExists("Medical Reports");
        createCategoryIfNotExists("Natural Tips");
        createCategoryIfNotExists("Lifestyle");
    }

    private void createRecordType(String typeName) {
        RecordType recordType = new RecordType();
        recordType.setType(typeName);
        recordTypeRepository.save(recordType);
    }
    private void createRecordTypeIfNotExists(String typeName) {
        // Check if the record type already exists
        RecordType existingRecordType = recordTypeRepository.findByType(typeName);
        if (existingRecordType == null) {
            // If not, create the record type
            RecordType recordType = new RecordType();
            recordType.setType(typeName);
            recordTypeRepository.save(recordType);
        }
    }

    private void createCategoryIfNotExists(String categoryName) {
        // Check if the record type already exists
        Category existingCategory = categoryRepository.findByCategory(categoryName);
        if (existingCategory == null) {
            // If not, create the record type
            Category category = new Category();
            category.setCategory(categoryName);
            categoryRepository.save(category);
        }
    }
}
