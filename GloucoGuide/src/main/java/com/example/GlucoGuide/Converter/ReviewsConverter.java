package com.example.GlucoGuide.Converter;

import com.example.GlucoGuide.DTO.ReviewsDTO;
import com.example.GlucoGuide.Entity.Reviews;
import org.springframework.stereotype.Component;

@Component
public class ReviewsConverter {

    public ReviewsDTO entityToDto(Reviews reviews) {
        ReviewsDTO dto = new ReviewsDTO();
        dto.setId(reviews.getId());
        dto.setValue(reviews.getValue());
        dto.setFeedback(reviews.getFeedback());
        dto.setDate(reviews.getDate());
        dto.setUserId(reviews.getUser() != null ? reviews.getUser().getUserId() : null);
        dto.setUserName(reviews.getUser() != null ? reviews.getUser().getUsername() : null);
        dto.setAdminId(reviews.getAdmin() != null ? reviews.getAdmin().getId() : null);
        dto.setAdminName(reviews.getAdmin() != null ? reviews.getAdmin().getUsername() : null);

        return dto;
    }

    public Reviews dtoToEntity(ReviewsDTO dto) {
        Reviews reviews = new Reviews();
        reviews.setId(dto.getId());
        reviews.setValue(dto.getValue());
        reviews.setFeedback(dto.getFeedback());
        reviews.setDate(dto.getDate());

        // User and Admin will be set in the service
        return reviews;
    }
}
