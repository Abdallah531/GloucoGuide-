package com.example.GlucoGuide.Converter;

import com.example.GlucoGuide.DTO.CategoryDTO;
import com.example.GlucoGuide.Entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryConverter {

    public CategoryDTO convertToDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryId(category.getCategoryId());
        categoryDTO.setCategory(category.getCategory());
        return categoryDTO;
    }

    public Category convertToEntity(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setCategoryId(categoryDTO.getCategoryId());
        category.setCategory(categoryDTO.getCategory());
        return category;
    }
}
